package com.fadymarty.rak_gpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.presentation.chat.ChatRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RakTheme {
                ChatRoot()
            }
        }
    }
}