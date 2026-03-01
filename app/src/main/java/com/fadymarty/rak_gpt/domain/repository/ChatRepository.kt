package com.fadymarty.rak_gpt.domain.repository

import com.fadymarty.rak_gpt.domain.model.MessageChunk
import com.fadymarty.rak_gpt.domain.model.requests.ChatRequest
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(request: ChatRequest): Flow<Result<MessageChunk>>
}