package com.fadymarty.rak_gpt.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.domain.model.requests.ChatRequest
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private fun sendMessage() {
        val message = Message(
            role = Role.USER,
            content = _state.value.message
        )
        _state.update {
            it.copy(
                message = "",
                messages = it.messages + message,
            )
        }

        val request = ChatRequest(
            model = "GigaChat",
            messages = _state.value.messages,
            stream = true
        )

        chatRepository.sendMessage(request)
            .onEach { result ->
                result
                    .onSuccess { chunk ->
                        val content = chunk.choices.first().delta.content
                        _state.update { state ->
                            val messages = state.messages.toMutableList()
                            val lastMessage = messages.last()
                            messages[messages.lastIndex] = lastMessage.copy(
                                content = lastMessage.content + content
                            )
                            state.copy(messages = messages)
                        }
                    }
            }.launchIn(viewModelScope)
    }
}