package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.data.data_source.remote.dto.MessageChunkDto
import com.fadymarty.rak_gpt.data.data_source.remote.dto.request.ChatRequestDto
import kotlinx.coroutines.flow.Flow

interface RemoteChatDataSource {
    fun sendMessage(request: ChatRequestDto): Flow<MessageChunkDto>
}