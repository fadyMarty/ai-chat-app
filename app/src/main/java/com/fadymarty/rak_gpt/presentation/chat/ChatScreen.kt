package com.fadymarty.rak_gpt.presentation.chat

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakPalette
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.presentation.chat.components.ChatMessage
import com.fadymarty.rak_gpt.presentation.chat.components.ChatOnboarding
import com.fadymarty.rak_gpt.presentation.components.app_bars.RakTopAppBar
import com.fadymarty.rak_gpt.presentation.components.buttons.RakFilledIconButton
import com.fadymarty.rak_gpt.presentation.components.buttons.RakIconButton
import com.fadymarty.rak_gpt.presentation.components.drawer.RakDrawerSheet
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons
import com.fadymarty.rak_gpt.presentation.components.text_fields.MessageTextField
import com.linc.audiowaveform.AudioWaveform
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.io.File

@Composable
fun ChatRoot(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChatScreen(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val messageFocusRequester = remember {
        FocusRequester()
    }

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            RakDrawerSheet(
                searchState = rememberTextFieldState(),
                chats = listOf(
                    "Web Page Design - CSS/HTML/JS",
                    "AI Impact On UI/UX Design",
                    "Web Page Design - CSS/HTML/JS",
                    "AI Impact On UI/UX Design",
                    "Web Page Design - CSS/HTML/JS",
                    "AI Impact On UI/UX Design"
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .windowInsetsPadding(
                    WindowInsets.ime.add(WindowInsets(bottom = 10.dp))
                )
        ) {
            RakTopAppBar(
                title = "Rak-GPT",
                onNavigationIconClick = {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                },
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
            AnimatedContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                targetState = state.messages.isEmpty(),
                transitionSpec = {
                    (fadeIn() + scaleIn())
                        .togetherWith(fadeOut() + scaleOut())
                }
            ) { isEmpty ->
                if (isEmpty) {
                    ChatOnboarding(
                        modifier = Modifier.fillMaxSize(),
                        suggestions = state.promptSuggestions,
                        onGetAnswerClick = {
                            onEvent(ChatEvent.SendMessage(it.prompt))
                        },
                        onEditPromptClick = {
                            onEvent(ChatEvent.EditPrompt(it.prompt))
                            messageFocusRequester.requestFocus()
                        },
                        userScrollEnabled = drawerState.isClosed
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
                            ChatMessage(
                                message = message,
                                amplitudes = message.amplitudes,
                                isPlaying = message.id == state.messageId && state.isPlaying,
                                onPlayPauseClick = {
                                    onEvent(ChatEvent.PlayPause(message))
                                },
                                onSeekForward = {
                                    onEvent(ChatEvent.SeekForward(message))
                                }
                            )
                        }
                    }
                }
            }
            MessageBar(
                focusRequester = messageFocusRequester,
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun MessageBar(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
) {
    val context = LocalContext.current
    val audioPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val outputFile = File(context.cacheDir, "audio.ogg")
            onEvent(ChatEvent.StartRecording(outputFile))
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        when {
            state.isLoading -> {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(CircleShape)
                        .background(RakTheme.colorScheme.black1)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 16.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Generating result for web...",
                        style = TextStyle(
                            fontFamily = NeurialGrotesk,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 1.32.em,
                            color = RakTheme.colorScheme.violet5
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = RakIcons.Hourglass,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.normal
                    )
                }
            }

            state.isRecording -> {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(CircleShape)
                        .background(RakTheme.colorScheme.black1)
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AudioWaveform(
                        modifier = Modifier.weight(1f),
                        amplitudes = state.amplitudes,
                        onProgressChange = {},
                        waveformBrush = Brush.linearGradient(
                            colors = listOf(Color(0xFF3793FF), Color(0xFFEB02FF))
                        ),
                        spikeWidth = 1.5.dp,
                        spikeRadius = 0.75.dp,
                        spikePadding = 4.dp
                    )
                    Box(
                        modifier = Modifier
                            .width(58.dp)
                            .fillMaxHeight()
                            .background(RakTheme.colorScheme.normal),
                        contentAlignment = Alignment.Center
                    ) {
                        val minutes = state.recordingSeconds / 60
                        val seconds = state.recordingSeconds % 60

                        Text(
                            text = "%d:%02d".format(minutes, seconds),
                            style = TextStyle(
                                fontFamily = NeurialGrotesk,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                lineHeight = 1.32.em,
                                letterSpacing = 0.sp,
                                color = RakTheme.colorScheme.black1
                            )
                        )
                    }
                }
            }

            else -> {
                MessageTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    state = state.messageState,
                    hint = "Ask what's on mind..."
                )
            }
        }

        when {
            state.isLoading -> {
                RakFilledIconButton(
                    modifier = Modifier.padding(vertical = 2.dp),
                    icon = RakIcons.Stop,
                    onClick = {
                        onEvent(ChatEvent.StopGeneration)
                    }
                )
            }

            state.isRecording -> {
                Box(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(52.dp)
                        .drawWithContent {
                            drawCircle(
                                color = RakPalette.Normal,
                                style = Stroke(
                                    width = 1.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(
                                            4.dp.toPx(),
                                            2.dp.toPx()
                                        )
                                    )
                                )
                            )
                            drawCircle(
                                color = RakPalette.Normal,
                                radius = (size.minDimension - 6.dp.toPx()) / 2f
                            )
                            drawCircle(
                                color = RakPalette.Black1,
                                radius = (size.minDimension - 6.dp.toPx()) / 2f,
                                alpha = 0.8f
                            )
                            drawContent()
                        }
                        .clip(CircleShape)
                        .clickable {
                            onEvent(ChatEvent.StopRecording)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = RakIcons.Mic,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.normal
                    )
                }
            }

            state.messageState.text.isNotBlank() -> {
                RakFilledIconButton(
                    modifier = Modifier.padding(vertical = 2.dp),
                    icon = RakIcons.Send,
                    onClick = {
                        onEvent(ChatEvent.SendMessage())
                    }
                )
            }

            else -> {
                RakFilledIconButton(
                    modifier = Modifier.padding(vertical = 2.dp),
                    icon = RakIcons.Mic,
                    onClick = {
                        audioPermissionResultLauncher.launch(
                            Manifest.permission.RECORD_AUDIO
                        )
                    }
                )
            }
        }
    }
}