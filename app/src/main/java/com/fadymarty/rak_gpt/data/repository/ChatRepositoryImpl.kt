package com.fadymarty.rak_gpt.data.repository

import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatApi
import com.fadymarty.rak_gpt.data.data_source.remote.dto.toMessageChunk
import com.fadymarty.rak_gpt.domain.model.MessageChunk
import com.fadymarty.rak_gpt.domain.model.requests.ChatRequest
import com.fadymarty.rak_gpt.domain.model.requests.toChatRequestDto
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ChatRepositoryImpl(
    private val gigaChatApi: GigaChatApi
) : ChatRepository {

    override fun sendMessage(
        request: ChatRequest
    ): Flow<Result<MessageChunk>> {
        return gigaChatApi.sendMessage(request.toChatRequestDto())
            .map {
                Result.success(it.toMessageChunk())
            }
            .catch {
                emit(Result.failure(it))
            }
    }
}