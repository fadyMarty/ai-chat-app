package com.fadymarty.rak_gpt.domain.model

import androidx.compose.ui.graphics.Color

data class PromptSuggestion(
    val title: String,
    val prompt: String,
    val color: Color,
)
