package com.fadymarty.rak_gpt.domain.model.requests

import com.fadymarty.rak_gpt.data.data_source.remote.dto.requests.ChatRequestDto
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.toMessageDto

data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean
)

fun ChatRequest.toChatRequestDto(): ChatRequestDto {
    return ChatRequestDto(
        model = model,
        messages = messages.map { it.toMessageDto() },
        stream = stream
    )
}