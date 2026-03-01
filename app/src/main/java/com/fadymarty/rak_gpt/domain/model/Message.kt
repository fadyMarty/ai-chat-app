package com.fadymarty.rak_gpt.domain.model

import com.fadymarty.rak_gpt.data.data_source.remote.dto.MessageDto

data class Message(
    val role: Role,
    val content: String
)

fun Message.toMessageDto(): MessageDto {
    return MessageDto(
        role = role,
        content = content
    )
}