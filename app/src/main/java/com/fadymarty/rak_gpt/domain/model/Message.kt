package com.fadymarty.rak_gpt.domain.model

import com.fadymarty.rak_gpt.data.data_source.remote.dto.MessageDto
import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val role: Role,
    val content: String? = null,
    val attachments: List<String>? = null,
    val amplitudes: List<Int> = emptyList(),
)

fun Message.toMessageDto(): MessageDto {
    return MessageDto(
        role = role.toRoleDto(),
        content = content,
        attachments = attachments
    )
}