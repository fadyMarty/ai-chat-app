package com.fadymarty.rak_gpt.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.toArgb
import dev.snipme.highlights.model.SyntaxTheme

@Composable
fun RakTheme(
    content: @Composable () -> Unit,
) {
    val colorScheme = RakColorScheme()

    CompositionLocalProvider(
        LocalRakColorScheme provides colorScheme
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = colorScheme.black3,
                onBackground = colorScheme.violet7,
                primary = colorScheme.normal,
                onPrimary = colorScheme.black1
            ),
            content = content
        )
    }
}

object RakTheme {

    val colorScheme: RakColorScheme
        @Composable
        get() = LocalRakColorScheme.current

    val syntaxTheme: SyntaxTheme
        get() = SyntaxTheme(
            key = "app",
            code = RakPalette.Violet4.toArgb(),
            keyword = RakPalette.Violet4.toArgb(),
            string = 0x0D9DFD,
            literal = RakPalette.Violet4.toArgb(),
            comment = RakPalette.Violet4.toArgb(),
            metadata = RakPalette.Violet4.toArgb(),
            multilineComment = RakPalette.Violet4.toArgb(),
            punctuation = RakPalette.Violet4.toArgb(),
            mark = RakPalette.Violet4.toArgb()
        )
}