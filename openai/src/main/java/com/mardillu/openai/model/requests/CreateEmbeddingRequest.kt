package com.mardillu.openai.model.requests

data class CreateEmbeddingRequest(
    val model: String,
    val input: String
)