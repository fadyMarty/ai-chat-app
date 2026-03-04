package com.fadymarty.rak_gpt.presentation.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RakIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    tint: Color,
) {
    Icon(
        modifier = modifier
            .size(24.dp)
            .clickable(
                interactionSource = null,
                indication = ripple(bounded = false),
                onClick = onClick
            ),
        imageVector = icon,
        contentDescription = null,
        tint = tint
    )
}