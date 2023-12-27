package com.heronet.shakespeareai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole

@Composable
fun UserBubble(message: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
        Spacer(modifier = Modifier.width(32.dp))
        Card(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(),
        ) {
            Text(
                text = message.content!!,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(8.dp)
                    .wrapContentSize(),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun AIBubble(message: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(.8f)
        ) {
            Text(
                text = message.content!!,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview
@Composable
fun BubblePrev() {
    Column {
        AIBubble(message = ChatMessage(role = ChatRole("user"), content = "Hello"))
        UserBubble(message = ChatMessage(role = ChatRole("user"), content = "Hello"))
    }
}