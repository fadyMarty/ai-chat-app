package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.data.data_source.remote.dto.request.ChatRequestDto
import com.fadymarty.rak_gpt.common.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRemoteDataSource {
    fun sendMessage(request: ChatRequestDto): Flow<Result<String>>
}