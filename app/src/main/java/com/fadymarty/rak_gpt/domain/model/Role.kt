package com.fadymarty.rak_gpt.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    @SerialName("user")
    USER,

    @SerialName("system")
    SYSTEM,

    @SerialName("assistant")
    ASSISTANT,

    @SerialName("function")
    FUNCTION
}