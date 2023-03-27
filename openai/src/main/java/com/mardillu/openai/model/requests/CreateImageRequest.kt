package com.mardillu.openai.model.requests

data class CreateImageRequest(
    val prompt: String,
    val n: Int,
    val size: String
)