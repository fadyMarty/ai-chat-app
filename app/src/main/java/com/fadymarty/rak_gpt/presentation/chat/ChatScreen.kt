package com.fadymarty.rak_gpt.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.presentation.chat.components.RakIconButton
import com.fadymarty.rak_gpt.presentation.chat.components.RakTextField
import com.fadymarty.rak_gpt.presentation.components.RakIcons
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
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

@Composable
private fun ChatScreen(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.messages) { message ->
                    val markdownState = rememberMarkdownState(
                        content = message.content,
                        retainState = true
                    )
                    val highlightsBuilder = Highlights.Builder()
                        .theme(RakTheme.syntaxTheme)
                    Markdown(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (message.role == Role.USER) {
                                    Modifier.align(Alignment.End)
                                } else {
                                    Modifier.align(Alignment.Start)
                                }
                            ),
                        markdownState = markdownState,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 37.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RakIconButton(
                    icon = RakIcons.EditSquare,
                    onClick = {}
                )
                RakTextField(
                    modifier = Modifier.weight(1f),
                    value = state.message,
                    onValueChange = {
                        onEvent(ChatEvent.MessageChanged(it))
                    },
                    hint = "Ask what's on mind..."
                )
                RakIconButton(
                    icon = RakIcons.Send,
                    onClick = {
                        onEvent(ChatEvent.SendMessage)
                    },
                    enabled = state.message.isNotBlank()
                )
            }
        }
    }
}