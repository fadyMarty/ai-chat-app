package com.fadymarty.rak_gpt.domain.repository

import com.fadymarty.rak_gpt.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(messages: List<Message>): Flow<Result<String>>
}