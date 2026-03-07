package com.fadymarty.rak_gpt.presentation.chat

import com.fadymarty.rak_gpt.domain.model.Message
import java.io.File

sealed interface ChatEvent {
    data class EditPrompt(val prompt: String) : ChatEvent
    data class SendMessage(val prompt: String? = null) : ChatEvent
    data object StopGeneration : ChatEvent
    data class StartRecording(val outputFile: File) : ChatEvent
    data object StopRecording : ChatEvent
    data class PlayPause(val message: Message) : ChatEvent
    data class SeekForward(val message: Message) : ChatEvent
}