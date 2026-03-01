package com.fadymarty.rak_gpt.presentation.chat

import com.fadymarty.rak_gpt.domain.model.Message

data class ChatState(
    val message: String = "",
    val messages: List<Message> = emptyList()
)
