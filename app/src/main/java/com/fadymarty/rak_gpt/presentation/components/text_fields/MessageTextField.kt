package com.fadymarty.rak_gpt.presentation.components.text_fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
fun MessageTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String,
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(RakTheme.colorScheme.black1)
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        state = state,
        cursorBrush = SolidColor(RakTheme.colorScheme.normal),
        textStyle = TextStyle(
            fontFamily = NeurialGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 1.32.em,
            color = RakTheme.colorScheme.violet5
        ),
        lineLimits = TextFieldLineLimits.MultiLine(
            maxHeightInLines = 6
        ),
        decorator = { innerTextField ->
            if (state.text.isEmpty()) {
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
    )
}