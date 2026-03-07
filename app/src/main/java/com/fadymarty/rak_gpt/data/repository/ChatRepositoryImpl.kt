package com.fadymarty.rak_gpt.data.repository

import android.content.Context
import com.fadymarty.rak_gpt.common.util.safeCall
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatApi
import com.fadymarty.rak_gpt.data.data_source.remote.RemoteChatDataSource
import com.fadymarty.rak_gpt.data.data_source.remote.dto.request.ChatRequestDto
import com.fadymarty.rak_gpt.domain.model.FileResponse
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.toMessageDto
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ChatRepositoryImpl(
    private val context: Context,
    private val remoteChatDataSource: RemoteChatDataSource,
    private val gigaChatApi: GigaChatApi,
) : ChatRepository {

    override fun sendMessage(messages: List<Message>): Flow<Result<String>> {
        val request = ChatRequestDto(
            model = "GigaChat-2-Pro",
            messages = messages.map { it.toMessageDto() },
            stream = true
        )

        return remoteChatDataSource.sendMessage(request)
            .map { chunk ->
                val content = chunk.choices.first().delta.content
                Result.success(content)
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override suspend fun uploadFile(file: File): Result<FileResponse> {
        return safeCall {
            val contentType = "audio/x-ogg".toMediaType()

            gigaChatApi.uploadFile(
                file = MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    file.asRequestBody(contentType)
                )
            ).toFileUploadResponse()
        }
    }

    override suspend fun downloadFile(id: String): Result<File> {
        return safeCall {
            val responseBody = gigaChatApi.downloadFile(id)
            val outputFile = File(context.cacheDir, "file.ogg")
            responseBody.byteStream().use { inputStream ->
                FileOutputStream(outputFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            outputFile
        }
    }
}