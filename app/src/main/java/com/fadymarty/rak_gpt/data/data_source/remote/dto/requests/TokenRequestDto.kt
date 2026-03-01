package com.fadymarty.rak_gpt.data.data_source.remote.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequestDto(
    val scope: String
)