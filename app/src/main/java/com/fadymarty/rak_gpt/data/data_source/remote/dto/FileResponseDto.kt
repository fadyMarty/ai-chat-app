package com.fadymarty.rak_gpt.data.data_source.remote.dto

import com.fadymarty.rak_gpt.domain.model.FileResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileResponseDto(
    val id: String,
    @SerialName("object")
    val objectX: String,
    val bytes: Int,
    @SerialName("access_policy")
    val accessPolicy: String,
    @SerialName("created_at")
    val createdAt: Int,
    val filename: String,
    val purpose: String,
    val modalities: List<String>,
) {
    fun toFileUploadResponse(): FileResponse {
        return FileResponse(
            id = id,
            objectX = objectX,
            bytes = bytes,
            accessPolicy = accessPolicy,
            createdAt = createdAt,
            filename = filename,
            purpose = purpose,
            modalities = modalities
        )
    }
}