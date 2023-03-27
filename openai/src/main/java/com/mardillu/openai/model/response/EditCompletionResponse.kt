package com.mardillu.openai.model.response

data class EditCompletionResponse(
    val `object`: String,
    val created: Int,
    val choices: List<Choice>,
    val usage: Usage
)