package com.heronet.shakespeareai

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ChatViewModel : ViewModel() {

    var messages by mutableStateOf(listOf<ChatMessage>())
        private set

    init {
        viewModelScope.launch {
            sendMessage("Hi!")
        }
    }

    suspend fun sendMessage(text: String) {
        val message = ChatMessage(role = ChatRole("user"), content = text)
        messages = messages + message

        val openai = OpenAI(
            token = "open-ai-token",
            timeout = Timeout(socket = 60.seconds),
            // additional configurations...
        )

        val request = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole("system"),
                    content = "You are Shakespeare. Always talk in shakespearean language"
                ),
            ) + messages
        )

        try {
            val res = openai.chatCompletion(request)
            messages += res.choices[0].message
        } catch (e: Exception) {
            Log.d("DDD", e.message.toString())
            e.printStackTrace()
        }
    }
}