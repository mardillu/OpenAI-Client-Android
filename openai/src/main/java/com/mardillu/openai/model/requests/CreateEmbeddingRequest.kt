package com.mardillu.openai.model.requests

data class CreateEmbeddingRequest(
    var model: String,
    var input: String
)