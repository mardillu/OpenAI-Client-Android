package com.mardillu.openai.model.response

data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Int,
    val choices: List<ChatChoice>,
    val usage: Usage,
)