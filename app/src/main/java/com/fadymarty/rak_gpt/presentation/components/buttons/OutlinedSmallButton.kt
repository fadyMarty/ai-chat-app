package com.fadymarty.rak_gpt.presentation.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme

@Composable
fun OutlinedSmallButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 19.dp,
        vertical = 11.5.dp
    ),
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = RakTheme.colorScheme.normal,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(contentPadding)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 1.32.em,
                textAlign = TextAlign.Center,
                color = RakTheme.colorScheme.normal
            )
        )
    }
}