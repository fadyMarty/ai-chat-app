package com.fadymarty.rak_gpt.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.MarkdownTypography
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

    val markdownTypography: MarkdownTypography
        @Composable
        get() = markdownTypography(
            h1 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            h2 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            h3 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            h4 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            h5 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            h6 = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            text = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            code = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 1.32.em,
                letterSpacing = 0.sp,
                color = Color(0xFF0FA958)
            ),
            inlineCode = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 1.32.em,
                letterSpacing = 0.sp,
                color = Color(0xFF0FA958)
            ),
            quote = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            paragraph = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            ordered = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            bullet = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            list = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            ),
            textLink = TextLinkStyles(
                style = SpanStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ),
                focusedStyle = SpanStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ),
                hoveredStyle = SpanStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ),
                pressedStyle = SpanStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            ),
            table = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.32.em
            )
        )

    val syntaxTheme: SyntaxTheme
        get() = SyntaxTheme(
            key = "app",
            code = RakPalette.Violet4.toArgb(),
            keyword = RakPalette.Normal.toArgb(),
            string = 0x0D9DFD,
            literal = RakPalette.Normal.toArgb(),
            comment = RakPalette.Violet4.toArgb(),
            metadata = RakPalette.Violet4.toArgb(),
            multilineComment = RakPalette.Violet4.toArgb(),
            punctuation = RakPalette.Normal.toArgb(),
            mark = RakPalette.Violet4.toArgb()
        )
}