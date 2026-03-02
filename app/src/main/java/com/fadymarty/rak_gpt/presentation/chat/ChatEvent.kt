package com.fadymarty.rak_gpt.presentation.chat

sealed interface ChatEvent {
    data class MessageChanged(val message: String) : ChatEvent
    data object SendMessage : ChatEvent
}