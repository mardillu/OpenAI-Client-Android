package com.mardillu.openai.model.requests

data class CreateThreadRequest(
    val messages: List<CreateMessageRequest>? = null,
    val metadata: Map<String, String>? = null
)
