package com.mardillu.openai.model.requests

data class ModerationRequest(
    val input: String,
    val model: String,
)