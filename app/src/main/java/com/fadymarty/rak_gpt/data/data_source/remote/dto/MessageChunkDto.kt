package com.fadymarty.rak_gpt.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageChunkDto(
    val choices: List<ChoiceDto>,
    val created: Long,
    val model: String,
    @SerialName("object")
    val obj: String,
    val usage: UsageDto? = null,
)