package com.fadymarty.rak_gpt.presentation.components.text_fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons

@Composable
fun SearchTexField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String? = null,
) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .background(RakTheme.colorScheme.black1)
            .padding(12.dp),
        state = state,
        cursorBrush = SolidColor(RakTheme.colorScheme.normal),
        textStyle = TextStyle(
            fontFamily = NeurialGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 1.32.em,
            color = RakTheme.colorScheme.violet4
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = RakIcons.Search,
                    contentDescription = null,
                    tint = RakTheme.colorScheme.violet3
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.text.isEmpty() && hint != null) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                fontFamily = NeurialGrotesk,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 1.32.em,
                                color = RakTheme.colorScheme.violet4
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}