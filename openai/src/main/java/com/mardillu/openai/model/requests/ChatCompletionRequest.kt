package com.mardillu.openai.model.requests

import com.mardillu.openai.model.Message

data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>
)