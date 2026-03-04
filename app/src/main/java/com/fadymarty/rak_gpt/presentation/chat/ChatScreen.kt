package com.fadymarty.rak_gpt.presentation.chat

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.presentation.chat.components.ChatOnboarding
import com.fadymarty.rak_gpt.presentation.components.app_bars.RakTopAppBar
import com.fadymarty.rak_gpt.presentation.components.buttons.FilledIconButton
import com.fadymarty.rak_gpt.presentation.components.buttons.RakIconButton
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons
import com.fadymarty.rak_gpt.presentation.components.text_fields.MessageTextField
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.rememberMarkdownState
import dev.snipme.highlights.Highlights
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatRoot(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChatScreen(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
) {
    val messageFocusRequester = remember {
        FocusRequester()
    }

    Scaffold(
        topBar = {
            RakTopAppBar(
                title = "Rak-GPT",
                onNavigationIconClick = {},
                actions = {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(
                                top = 14.dp,
                                bottom = 18.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        RakIconButton(
                            icon = RakIcons.Share,
                            onClick = {},
                            tint = Color(0xFF141B34)
                        )
                        RakIconButton(
                            icon = RakIcons.MoreVert,
                            onClick = {},
                            tint = Color(0xFF141B34)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    ),
                targetState = state.messages.isEmpty(),
                transitionSpec = {
                    fadeIn() + scaleIn() togetherWith
                            fadeOut() + scaleOut()
                }
            ) { isEmpty ->
                if (isEmpty) {
                    ChatOnboarding(
                        suggestions = state.promptSuggestions,
                        onGetAnswerClick = {
                            onEvent(ChatEvent.SendMessage(it.prompt))
                        },
                        onEditPromptClick = {
                            onEvent(ChatEvent.EditPrompt(it.prompt))
                            messageFocusRequester.requestFocus()
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        reverseLayout = true,
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
                        contentPadding = PaddingValues(
                            horizontal = 24.dp,
                            vertical = 40.dp
                        )
                    ) {
                        items(state.messages) { message ->
                            val isFromAssistant = message.role == Role.ASSISTANT
                            val markdownState = rememberMarkdownState(
                                content = message.content,
                                retainState = true
                            )
                            val highlightsBuilder = Highlights.Builder().theme(RakTheme.syntaxTheme)
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(7.36.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isFromAssistant) {
                                        Icon(
                                            modifier = Modifier.size(24.dp, 20.dp),
                                            imageVector = RakIcons.SmartToy,
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = if (isFromAssistant) {
                                            "Rak-GPT"
                                        } else "You",
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
                                Markdown(
                                    modifier = Modifier.fillMaxWidth(),
                                    markdownState = markdownState,
                                    typography = markdownTypography(
                                        paragraph = TextStyle(
                                            fontFamily = NeurialGrotesk,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp,
                                            lineHeight = 1.32.em
                                        ),
                                        code = TextStyle(
                                            fontFamily = NeurialGrotesk,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            lineHeight = 1.32.em,
                                            letterSpacing = 0.sp
                                        ),
                                        inlineCode = TextStyle(
                                            fontFamily = NeurialGrotesk,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            lineHeight = 1.32.em,
                                            letterSpacing = 0.sp
                                        )
                                    ),
                                    components = markdownComponents(
                                        codeFence = {
                                            MarkdownHighlightedCodeFence(
                                                content = it.content,
                                                node = it.node,
                                                highlightsBuilder = highlightsBuilder,
                                                showHeader = true
                                            )
                                        },
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .navigationBarsPadding()
                    .windowInsetsPadding(
                        WindowInsets.ime.add(
                            WindowInsets(bottom = 10.dp)
                        )
                    ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                MessageTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(messageFocusRequester),
                    state = state.promptState,
                    hint = "Ask what's on mind..."
                )
                FilledIconButton(
                    modifier = Modifier.padding(vertical = 2.dp),
                    icon = RakIcons.Send,
                    onClick = {
                        onEvent(ChatEvent.SendMessage())
                    },
                    enabled = state.promptState.text.isNotBlank()
                )
            }
        }
    }
}