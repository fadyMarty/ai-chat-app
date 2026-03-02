package com.fadymarty.rak_gpt.data.data_source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val role: RoleDto,
    val content: String,
)