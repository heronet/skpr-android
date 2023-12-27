package com.heronet.shakespeareai

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aallam.openai.api.chat.ChatMessage

@Composable
fun BubblesContainer(messages: List<ChatMessage>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        state = listState
    ) {
        items(messages) { message ->
            when (message.role.role) {
                "user" -> UserBubble(message = message)
                "assistant" -> AIBubble(message = message)
            }
        }

    }
    LaunchedEffect(key1 = messages) {
        listState.animateScrollToItem(messages.size - 1)
    }
}