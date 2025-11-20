package com.mardillu.openai.model.requests

@Deprecated("Use ChatCompletionRequest instead")
data class EditCompletionRequest(
    val model: String,
    val input: String,
    val instruction: String
)