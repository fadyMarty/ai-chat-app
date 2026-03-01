package com.fadymarty.rak_gpt.domain.model

data class Usage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
    val precachedPromptTokens: Int
)