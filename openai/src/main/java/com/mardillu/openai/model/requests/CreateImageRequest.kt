package com.mardillu.openai.model.requests

data class CreateImageRequest(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024",
    val model: String? = "dall-e-2",
    val quality: String? = "standard",
    val style: String? = "vivid",
    val response_format: String? = "url",
    val user: String? = null
)