package com.fadymarty.rak_gpt.data.data_source.remote.dto

import com.fadymarty.rak_gpt.domain.model.MessageChunk
import kotlinx.serialization.Serializable

@Serializable
data class MessageChunkDto(
    val choices: List<ChoiceDto>,
    val created: Long,
    val model: String,
    val usage: UsageDto
)

fun MessageChunkDto.toMessageChunk(): MessageChunk {
    return MessageChunk(
        choices = choices.map { it.toChoice() },
        created = created,
        model = model,
        usage = usage.toUsage()
    )
}