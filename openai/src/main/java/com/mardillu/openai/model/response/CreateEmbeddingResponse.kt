package com.mardillu.openai.model.response

data class CreateEmbeddingResponse(
    val `object`: String,
    val `data`: List<Data>,
    val model: String,
    val usage: Usage
)