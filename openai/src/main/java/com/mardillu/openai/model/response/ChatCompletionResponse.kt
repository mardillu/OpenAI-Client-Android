package com.mardillu.openai.model.response

data class ChatCompletionResponse(
    var id: String,
    var `object`: String,
    var created: Int,
    var choices: List<ChatChoice>,
    var usage: Usage,
)