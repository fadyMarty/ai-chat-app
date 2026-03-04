package com.fadymarty.rak_gpt.presentation.chat

sealed interface ChatEvent {
    data class EditPrompt(val prompt: String) : ChatEvent
    data class SendMessage(val prompt: String? = null) : ChatEvent
}