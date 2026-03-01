package com.fadymarty.rak_gpt.data.data_source.remote.dto


import com.fadymarty.rak_gpt.domain.model.Delta
import kotlinx.serialization.Serializable

@Serializable
data class DeltaDto(
    val content: String
)

fun DeltaDto.toDelta(): Delta {
    return Delta(
        content = content
    )
}