package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.common.util.Constants
import com.fadymarty.rak_gpt.data.data_source.remote.dto.MessageChunkDto
import com.fadymarty.rak_gpt.data.data_source.remote.dto.requests.ChatRequestDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources

class GigaChatApi(
    private val okHttpClient: OkHttpClient,
    private val json: Json
) {
    fun sendMessage(request: ChatRequestDto): Flow<MessageChunkDto> {
        return callbackFlow {
            val body = json.encodeToString(request).toRequestBody()

            val sseRequest = Request.Builder()
                .url("${Constants.BASE_URL}/chat/completions")
                .post(body)
                .build()

            val listener = object : EventSourceListener() {
                override fun onEvent(
                    eventSource: EventSource,
                    id: String?,
                    type: String?,
                    data: String
                ) {
                    if (data == "[DONE]") {
                        close()
                        return
                    }

                    val chunk = json.decodeFromString<MessageChunkDto>(data)
                    trySend(chunk)
                }

                override fun onFailure(
                    eventSource: EventSource,
                    t: Throwable?,
                    response: Response?
                ) {
                    close(t)
                }

                override fun onClosed(eventSource: EventSource) {
                    close()
                }
            }

            val eventSource = EventSources
                .createFactory(okHttpClient)
                .newEventSource(sseRequest, listener)

            awaitClose {
                eventSource.cancel()
            }
        }
    }
}