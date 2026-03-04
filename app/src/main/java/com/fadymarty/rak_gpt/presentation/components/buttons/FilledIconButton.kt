package com.fadymarty.rak_gpt.presentation.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.fadymarty.rak_gpt.common.theme.RakTheme

@Composable
fun FilledIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.32f
    )
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .alpha(animatedAlpha)
            .background(RakTheme.colorScheme.normal)
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            contentDescription = null,
            tint = RakTheme.colorScheme.black1
        )
    }
}