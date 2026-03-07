package com.fadymarty.rak_gpt.presentation.chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.fadymarty.rak_gpt.R
import com.fadymarty.rak_gpt.common.theme.NeurialGrotesk
import com.fadymarty.rak_gpt.domain.model.PromptSuggestion

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChatOnboarding(
    modifier: Modifier = Modifier,
    suggestions: List<PromptSuggestion>,
    onGetAnswerClick: (PromptSuggestion) -> Unit,
    onEditPromptClick: (PromptSuggestion) -> Unit,
    userScrollEnabled: Boolean,
) {
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = 1
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(37.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(
                        width = 75.13.dp,
                        height = 91.dp
                    ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_robot),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(19.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Hello, Boss!\nAm Ready For Help You",
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 1.32.em,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.76f),
                        text = "Ask me anything what's are on your mind. Am here to assist you!",
                        fontFamily = NeurialGrotesk,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 1.32.em,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        item {
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = !WindowInsets.isImeVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(Modifier.height(37.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        state = lazyListState,
                        userScrollEnabled = userScrollEnabled,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 60.dp)
                    ) {
                        items(suggestions) { suggestion ->
                            PromptSuggestion(
                                suggestion = suggestion,
                                onGetAnswerClick = {
                                    onGetAnswerClick(suggestion)
                                },
                                onEditClick = {
                                    onEditPromptClick(suggestion)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}