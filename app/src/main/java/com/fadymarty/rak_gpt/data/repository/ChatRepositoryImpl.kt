package com.fadymarty.rak_gpt.data.repository

import com.fadymarty.rak_gpt.data.data_source.remote.RemoteChatDataSource
import com.fadymarty.rak_gpt.data.data_source.remote.dto.request.ChatRequestDto
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.toMessageDto
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ChatRepositoryImpl(
    private val remoteChatDataSource: RemoteChatDataSource,
) : ChatRepository {

    override fun sendMessage(messages: List<Message>): Flow<Result<String>> {
        val request = ChatRequestDto(
            model = "GigaChat",
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
}