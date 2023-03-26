package com.mardillu.openai.model.response

data class CreateEmbeddingResponse(
    var `object`: String,
    var `data`: List<Data>,
    var model: String,
    var usage: Usage
)