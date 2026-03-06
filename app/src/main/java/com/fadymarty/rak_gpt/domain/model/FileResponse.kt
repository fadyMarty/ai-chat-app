package com.fadymarty.rak_gpt.domain.model

data class FileResponse(
    val id: String,
    val objectX: String,
    val bytes: Int,
    val accessPolicy: String,
    val createdAt: Int,
    val filename: String,
    val purpose: String,
    val modalities: List<String>,
)