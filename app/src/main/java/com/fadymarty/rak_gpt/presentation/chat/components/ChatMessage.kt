package com.fadymarty.rak_gpt.presentation.chat.components

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons
import com.linc.audiowaveform.AudioWaveform
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownCodeFence
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.rememberMarkdownState
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight
import dev.snipme.highlights.model.SyntaxLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    message: Message,
    amplitudes: List<Int>,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onSeekForward: () -> Unit,
) {
    val isFromAssistant = message.role == Role.ASSISTANT

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .then(
                        if (isFromAssistant) {
                            Modifier.size(24.dp, 20.dp)
                        } else Modifier.size(24.dp)
                    ),
                imageVector = if (isFromAssistant) {
                    RakIcons.SmartToy
                } else RakIcons.SentimentSatisfied,
                contentDescription = null,
                tint = RakTheme.colorScheme.darkHover
            )
            Text(
                text = if (isFromAssistant) {
                    "Rak-GPT"
                } else "Wow Rakibul",
                style = TextStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 18.39.sp,
                    letterSpacing = (-0.22).sp,
                    color = RakTheme.colorScheme.darker
                )
            )
        }
        if (message.content != null) {
            val markdownState = rememberMarkdownState(
                content = message.content,
                retainState = true
            )
            val highlightsBuilder = Highlights.Builder().theme(RakTheme.syntaxTheme)

            SelectionContainer(
                modifier = Modifier.fillMaxWidth()
            ) {
                Markdown(
                    modifier = Modifier.fillMaxWidth(),
                    markdownState = markdownState,
                    typography = RakTheme.markdownTypography,
                    components = markdownComponents(
                        codeFence = {
                            MarkdownCodeFence(
                                content = it.content,
                                node = it.node,
                                style = it.typography.code
                            ) { code, language, style ->
                                val codeHighlights: AnnotatedString by produceHighlightsState(
                                    code = code,
                                    language = language,
                                    highlightsBuilder = highlightsBuilder
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(RakTheme.colorScheme.black1)
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    CodeTopBar(
                                        language = language,
                                        code = code
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(RakTheme.colorScheme.black3)
                                    ) {
                                        MarkdownBasicText(
                                            modifier = Modifier
                                                .horizontalScroll(rememberScrollState())
                                                .padding(12.dp),
                                            text = codeHighlights,
                                            style = style
                                        )
                                    }
                                }
                            }
                        }
                    )
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(RakTheme.colorScheme.black1)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = onPlayPauseClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(1.25.dp)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(RakTheme.colorScheme.normal)
                    )
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = if (isPlaying) {
                            RakIcons.Stop
                        } else RakIcons.PlayArrow,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.black1
                    )
                }
                AudioWaveform(
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            scaleY = 20.dp / 48.dp
                        },
                    amplitudes = amplitudes,
                    onProgressChange = {},
                    waveformBrush = Brush.linearGradient(
                        colors = listOf(Color(0xFF3793FF), Color(0xFFEB02FF))
                    ),
                    spikeWidth = 1.5.dp,
                    spikeRadius = 0.75.dp,
                    spikePadding = 4.dp
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = null,
                            indication = ripple(bounded = false),
                            onClick = onSeekForward
                        ),
                    imageVector = RakIcons.ForwardCircle,
                    contentDescription = null,
                    tint = RakTheme.colorScheme.normal
                )
            }
        }
    }
}

@Composable
private fun CodeTopBar(
    modifier: Modifier = Modifier,
    language: String?,
    code: String,
) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    DisableSelection {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language?.uppercase() ?: "",
                style = TextStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 1.32.em
                )
            )
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        scope.launch {
                            val clipData = ClipData.newPlainText(language, code)
                            val clipEntry = ClipEntry(clipData)
                            clipboard.setClipEntry(clipEntry)
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = RakIcons.ContentCopy,
                    contentDescription = null,
                    tint = Color(0xFF141B34)
                )
                Text(
                    text = "COPY",
                    style = TextStyle(
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 1.32.em
                    )
                )
            }
        }
    }
}

@Composable
private fun produceHighlightsState(
    code: String,
    language: String?,
    highlightsBuilder: Highlights.Builder,
): State<AnnotatedString> {
    return produceState(
        initialValue = AnnotatedString(text = code),
        key1 = code,
    ) {
        val job = launch(Dispatchers.Default) {
            value = buildHighlightedAnnotatedString(code, language, highlightsBuilder)
        }
        awaitDispose {
            job.cancel()
        }
    }
}

private fun buildHighlightedAnnotatedString(
    code: String,
    language: String?,
    highlightsBuilder: Highlights.Builder,
): AnnotatedString {
    val syntaxLanguage = language?.let { SyntaxLanguage.getByName(it) }
    val codeHighlights = highlightsBuilder
        .code(code)
        .let { if (syntaxLanguage != null) it.language(syntaxLanguage) else it }
        .build()
        .getHighlights()
    return buildAnnotatedString {
        append(code)
        codeHighlights.forEach {
            val style = when (it) {
                is ColorHighlight -> SpanStyle(color = Color(it.rgb).copy(alpha = 1f))
                is BoldHighlight -> SpanStyle(fontWeight = FontWeight.Bold)
            }
            addStyle(
                style = style,
                start = it.location.start,
                end = it.location.end,
            )
        }
    }
}