package com.fadymarty.rak_gpt.presentation.chat

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.graphics.Color
import com.fadymarty.rak_gpt.common.theme.RakPalette
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.PromptSuggestion
import java.io.File

data class ChatState(
    val promptSuggestions: List<PromptSuggestion> = listOf(
        PromptSuggestion(
            title = "Try Fix Bug From Your Code",
            prompt = "Fix my bug from below code. I can’t find where is the issue. Do it in details and explain me why happeni",
            color = Color(0xFFF24E1E)
        ),
        PromptSuggestion(
            title = "Try Fix Bug From Your Code",
            prompt = "Fix my bug from below code. I can’t find where is the issue. Do it in details and explain me why happeni",
            color = RakPalette.Normal
        ),
        PromptSuggestion(
            title = "Try Fix Bug From Your Code",
            prompt = "Fix my bug from below code. I can’t find where is the issue. Do it in details and explain me why happeni",
            color = Color(0xFF0FA958)
        )
    ),
    val isLoading: Boolean = false,
    val isRecording: Boolean = false,
    val messageState: TextFieldState = TextFieldState(),
    val messages: List<Message> = emptyList(),
    val audioFile: File? = null,
    val amplitudes: List<Int> = emptyList(),
    val recordingSeconds: Int = 0,
    val playingMessageId: String? = null,
)