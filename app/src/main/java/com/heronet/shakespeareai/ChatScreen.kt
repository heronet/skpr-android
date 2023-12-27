package com.heronet.shakespeareai


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val chatViewModel = viewModel<ChatViewModel>()

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Shakespeare AI") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        val messages = chatViewModel.messages

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (messages.isNotEmpty()) {
                BubblesContainer(
                    messages,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Welcome(modifier = Modifier.weight(1f))
            }
            ChatBar(
                onSend = { message ->
                    if (message.isNotEmpty()) {
                        scope.launch {
                            chatViewModel.sendMessage(message)
                        }
                    }
                },
                isLoading = chatViewModel.isLoading
            )
        }
    }

}

@Preview
@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "I am William Shakespeare",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Pray, join me in a chat of wondrous words and thoughtful discourse",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}