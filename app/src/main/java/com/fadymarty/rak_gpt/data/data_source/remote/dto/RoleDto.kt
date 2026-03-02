package com.fadymarty.rak_gpt.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoleDto {
    @SerialName("user")
    USER,

    @SerialName("system")
    SYSTEM,

    @SerialName("assistant")
    ASSISTANT,

    @SerialName("function")
    FUNCTION
}