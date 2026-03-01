package com.fadymarty.rak_gpt.data.data_source.remote.dto

import com.fadymarty.rak_gpt.domain.model.Role
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val role: Role,
    val content: String
)