package com.fadymarty.rak_gpt.presentation.components.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk

@Composable
fun DrawerChatItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        text = title,
        style = TextStyle(
            fontFamily = NeurialGrotesk,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}