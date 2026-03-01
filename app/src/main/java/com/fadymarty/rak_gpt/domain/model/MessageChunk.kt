package com.fadymarty.rak_gpt.domain.model

data class MessageChunk(
    val choices: List<Choice>,
    val created: Long,
    val model: String,
    val usage: Usage
)