package com.fadymarty.rak_gpt.presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme

@Composable
fun RakTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(RakTheme.colorScheme.normal),
        textStyle = TextStyle(
            fontFamily = NeurialGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 1.32.em,
            color = RakTheme.colorScheme.violet5
        )
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(RakTheme.colorScheme.black1)
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
        ) {
            if (value.isEmpty() && hint != null) {
                Text(
                    text = hint,
                    style = TextStyle(
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 1.32.em,
                        color = RakTheme.colorScheme.violet5
                    )
                )
            }
            innerTextField()
        }
    }
}