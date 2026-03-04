package com.fadymarty.rak_gpt.presentation.chat

import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ChatViewModel(
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.EditPrompt -> {
                _state.value.promptState.setTextAndPlaceCursorAtEnd(event.prompt)
            }

            is ChatEvent.SendMessage -> onSendMessage(event.prompt)
        }
    }

    private fun onSendMessage(prompt: String?) {
        val userMessage = Message(
            role = Role.USER,
            content = prompt ?: _state.value.promptState.text.toString()
        )
        val assistantMessage = Message(
            role = Role.ASSISTANT,
            content = ""
        )
        _state.update {
            it.copy(
                messages = it.messages.toMutableList().apply {
                    add(0, userMessage)
                    add(0, assistantMessage)
                }
            )
        }
        _state.value.promptState.clearText()
        chatRepository.sendMessage(
            messages = _state.value.messages.drop(1)
        ).onEach { result ->
            result
                .onSuccess { content ->
                    _state.update {
                        it.copy(
                            messages = it.messages.toMutableList().apply {
                                val firstMessage = it.messages.first()
                                set(
                                    index = 0,
                                    element = firstMessage.copy(
                                        content = firstMessage.content + content
                                    )
                                )
                            }
                        )
                    }
                }
        }.launchIn(viewModelScope)
    }
}