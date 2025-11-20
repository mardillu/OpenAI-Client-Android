package com.mardillu.openai.model.requests

data class CreateMessageRequest(
    val role: String,
    val content: String,
    val file_ids: List<String>? = null,
    val metadata: Map<String, String>? = null
)
