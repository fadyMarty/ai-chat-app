package com.fadymarty.rak_gpt.presentation.components.app_bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.presentation.components.buttons.RakIconButton
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons

@Composable
fun RakTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit,
    actions: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 24.dp)
    ) {
        RakIconButton(
            modifier = Modifier
                .padding(
                    top = 18.dp,
                    bottom = 14.dp
                ),
            icon = RakIcons.Menu,
            onClick = onNavigationIconClick,
            tint = Color(0xFF141B34)
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = TextStyle(
                fontFamily = NeurialGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 1.32.em,
                textAlign = TextAlign.Center
            )
        )
        actions()
    }
}