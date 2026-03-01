package com.fadymarty.rak_gpt.data.data_source.remote.dto

import com.fadymarty.rak_gpt.domain.model.Usage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsageDto(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int,
    @SerialName("precached_prompt_tokens")
    val precachedPromptTokens: Int
)

fun UsageDto.toUsage(): Usage {
    return Usage(
        promptTokens = promptTokens,
        completionTokens = completionTokens,
        totalTokens = totalTokens,
        precachedPromptTokens = precachedPromptTokens
    )
}