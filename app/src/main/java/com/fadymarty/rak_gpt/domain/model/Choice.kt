package com.fadymarty.rak_gpt.domain.model

data class Choice(
    val delta: Delta,
    val index: Int,
    val finishReason: String
)