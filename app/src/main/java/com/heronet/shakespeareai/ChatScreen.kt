package com.heronet.shakespeareai


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val chatViewModel = viewModel<ChatViewModel>()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Shakespeare") })
        }
    ) { padding ->
        val messages = chatViewModel.messages

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BubblesContainer(
                messages,
                modifier = Modifier.weight(1f)
            )
            ChatBar(
                onSend = { message ->
                    if (message.isNotEmpty()) {
                        scope.launch {
                            chatViewModel.sendMessage(message)
                        }
                    }
                }
            )
        }
    }

}

@Composable
fun BubblesContainer(messages: List<ChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(messages) { message ->
            when (message.role.role) {
                "user" -> UserBubble(message = message)
                "assistant" -> AIBubble(message = message)
            }
        }
    }
}

@Composable
fun ChatBar(onSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text(text = "Type a message...") },
            label = { Text(text = "Message") },
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = {
                onSend(message)
                message = ""
            },
            enabled = message.isNotEmpty()
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send message")
        }
    }
}


@Composable
fun UserBubble(message: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
        Spacer(modifier = Modifier.width(32.dp))
        Card(
            modifier = Modifier
                .padding(8.dp)
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
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Left) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(.8f),
        ) {
            Text(
                text = message.content!!,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview
@Composable
fun BubblePrev() {
    AIBubble(message = ChatMessage(role = ChatRole("user"), content = "Hello"))
}