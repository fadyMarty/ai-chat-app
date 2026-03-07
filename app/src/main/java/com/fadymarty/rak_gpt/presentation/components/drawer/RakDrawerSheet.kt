package com.fadymarty.rak_gpt.presentation.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.common.theme.RakTheme
import com.fadymarty.rak_gpt.presentation.components.icons.RakIcons
import com.fadymarty.rak_gpt.presentation.components.text_fields.SearchTexField

@Composable
fun RakDrawerSheet(
    modifier: Modifier = Modifier,
    searchState: TextFieldState,
    chats: List<String>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight()
            .safeDrawingPadding()
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    offset = DpOffset(20.dp, 0.dp),
                    radius = 40.dp,
                    spread = (-40).dp,
                    color = Color(0xFFA0B0BF),
                    alpha = 0.25f
                )
            )
            .background(RakTheme.colorScheme.black3)
            .padding(vertical = 16.dp)
    ) {
        SearchTexField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 20.dp),
            state = searchState,
            hint = "Search chat history"
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                start = 24.dp,
                top = 24.dp,
                end = 20.dp,
                bottom = 24.dp
            )
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp, 23.33.dp),
                        imageVector = RakIcons.SmartToy,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.darkHover
                    )
                    Text(
                        text = "Rak-GPT",
                        style = TextStyle(
                            fontFamily = NeurialGrotesk,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            lineHeight = 20.sp,
                            color = RakTheme.colorScheme.darker
                        )
                    )
                }
                Spacer(Modifier.height(14.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {}
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = RakIcons.PageInfo,
                            contentDescription = null,
                            tint = RakTheme.colorScheme.violet6
                        )
                        Text(
                            modifier = Modifier.alpha(0.8f),
                            text = "Customize Feed",
                            style = TextStyle(
                                fontFamily = NeurialGrotesk,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                lineHeight = 20.sp,
                                color = RakTheme.colorScheme.darker
                            )
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(0.8f),
                        imageVector = RakIcons.keyboardArrowRight,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.darker
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {}
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = RakIcons.Language,
                        contentDescription = null,
                        tint = RakTheme.colorScheme.violet6
                    )
                    Text(
                        modifier = Modifier.alpha(0.8f),
                        text = "Community",
                        style = TextStyle(
                            fontFamily = NeurialGrotesk,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            lineHeight = 20.sp,
                            color = RakTheme.colorScheme.darker
                        )
                    )
                }
                Spacer(Modifier.height(14.dp))
            }
            item {
                HorizontalDivider(
                    color = RakTheme.colorScheme.black5
                )
                Spacer(Modifier.height(24.dp))
            }
            item {
                Text(
                    text = "Recent Chats",
                    style = TextStyle(
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = RakTheme.colorScheme.darker
                    )
                )
                Spacer(Modifier.height(20.dp))
            }
            items(chats.take(4)) { chat ->
                DrawerChatItem(
                    title = chat,
                    onClick = {}
                )
            }
            item {
                Spacer(Modifier.height(28.dp))
                Text(
                    text = "Last Months",
                    style = TextStyle(
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = RakTheme.colorScheme.darker
                    )
                )
                Spacer(Modifier.height(20.dp))
            }
            items(chats.takeLast(2)) { chat ->
                DrawerChatItem(
                    title = chat,
                    onClick = {}
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = RakIcons.SentimentSatisfied,
                    contentDescription = null,
                    tint = RakTheme.colorScheme.darkHover
                )
                Text(
                    text = "Wow Rakibul",
                    style = TextStyle(
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        lineHeight = 1.32.em
                    )
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFF612F).copy(alpha = 0.48f),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(
                        color = Color(0xFFFF612F).copy(alpha = 0.08f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = RakIcons.IosShare,
                    contentDescription = null,
                    tint = Color(0xFFFF612F)
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun RakDrawerSheetPreview() {
    RakTheme {
        RakDrawerSheet(
            searchState = rememberTextFieldState(),
            chats = listOf(
                "Web Page Design - CSS/HTML/JS",
                "AI Impact On UI/UX Design",
                "Web Page Design - CSS/HTML/JS",
                "AI Impact On UI/UX Design",
                "Web Page Design - CSS/HTML/JS",
                "AI Impact On UI/UX Design"
            )
        )
    }
}