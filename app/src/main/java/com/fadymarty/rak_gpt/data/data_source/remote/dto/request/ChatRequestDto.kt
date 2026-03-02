package com.fadymarty.rak_gpt.data.data_source.remote.dto.request

import com.fadymarty.rak_gpt.data.data_source.remote.dto.MessageDto
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequestDto(
    val model: String,
    val messages: List<MessageDto>,
    val stream: Boolean,
)