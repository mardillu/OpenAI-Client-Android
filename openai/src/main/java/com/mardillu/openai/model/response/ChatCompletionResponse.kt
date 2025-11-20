package com.mardillu.openai.model.response

data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val system_fingerprint: String? = null,
    val choices: List<ChatChoice>,
    val usage: Usage,
)