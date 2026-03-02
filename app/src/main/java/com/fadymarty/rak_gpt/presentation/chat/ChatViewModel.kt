package com.fadymarty.rak_gpt.presentation.chat

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
            is ChatEvent.MessageChanged -> {
                _state.update {
                    it.copy(
                        message = event.message
                    )
                }
            }

            ChatEvent.SendMessage -> onSendMessage()
        }
    }

    private fun onSendMessage() {
        val userMessage = Message(
            role = Role.USER,
            content = _state.value.message
        )
        val assistantMessage = Message(
            role = Role.ASSISTANT,
            content = ""
        )
        _state.update {
            it.copy(
                messages = it.messages + userMessage + assistantMessage,
                message = ""
            )
        }
        chatRepository.sendMessage(
            messages = _state.value.messages.dropLast(1)
        ).onEach { result ->
            result
                .onSuccess { content ->
                    _state.update {
                        val messages = it.messages.toMutableList()
                        val lastMessage = messages.last()
                        messages[messages.lastIndex] = lastMessage.copy(
                            content = lastMessage.content + content
                        )
                        it.copy(messages = messages)
                    }
                }
        }.launchIn(viewModelScope)
    }
}