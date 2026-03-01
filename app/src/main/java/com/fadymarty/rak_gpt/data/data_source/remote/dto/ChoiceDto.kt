package com.fadymarty.rak_gpt.data.data_source.remote.dto

import com.fadymarty.rak_gpt.domain.model.Choice
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChoiceDto(
    val delta: DeltaDto,
    val index: Int,
    @SerialName("finish_reason")
    val finishReason: String
)

fun ChoiceDto.toChoice(): Choice {
    return Choice(
        delta = delta.toDelta(),
        index = index,
        finishReason = finishReason
    )
}