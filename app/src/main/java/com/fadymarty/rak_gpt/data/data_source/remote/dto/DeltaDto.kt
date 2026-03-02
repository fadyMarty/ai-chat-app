package com.fadymarty.rak_gpt.data.data_source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeltaDto(
    val content: String,
    val role: RoleDto? = null,
)