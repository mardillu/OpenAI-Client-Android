package com.mardillu.openai.model.requests

data class EditCompletionRequest(
    val model: String,
    val input: String,
    val instruction: String
)