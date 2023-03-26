package com.mardillu.openai.model.requests

import com.mardillu.openai.model.Message

data class ChatCompletionRequest(
    var model: String,
    var messages: List<Message>
)