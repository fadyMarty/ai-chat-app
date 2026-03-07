package com.fadymarty.rak_gpt.presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.domain.model.PromptSuggestion
import com.fadymarty.rak_gpt.presentation.components.buttons.OutlinedSmallButton
import com.fadymarty.rak_gpt.presentation.components.buttons.SmallButton
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons

@Composable
fun PromptSuggestion(
    modifier: Modifier = Modifier,
    suggestion: PromptSuggestion,
    onGetAnswerClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(252.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(RakTheme.colorScheme.black1)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(suggestion.color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = RakIcons.DataObject,
                contentDescription = null,
                tint = RakTheme.colorScheme.black1
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = suggestion.title,
                style = TextStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 1.32.em
                )
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = suggestion.prompt,
                style = TextStyle(
                    fontFamily = NeurialGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 1.32.em,
                    color = RakTheme.colorScheme.violet4
                ),
                overflow = TextOverflow.Ellipsis,
                minLines = 3,
                maxLines = 3
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallButton(
                label = "Get Answer",
                onClick = onGetAnswerClick
            )
            OutlinedSmallButton(
                label = "Edit Prompt",
                onClick = onEditClick
            )
        }
    }
}