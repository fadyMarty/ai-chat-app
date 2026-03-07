package com.fadymarty.rak_gpt.domain.repository

import com.fadymarty.rak_gpt.domain.model.FileResponse
import com.fadymarty.rak_gpt.domain.model.Message
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ChatRepository {
    fun sendMessage(messages: List<Message>): Flow<Result<String>>
    suspend fun uploadFile(file: File): Result<FileResponse>
    suspend fun downloadFile(id: String): Result<File>
}