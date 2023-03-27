package com.mardillu.openai.model.response

data class CreateImageResponse(
    val created: Int,
    val `data`: List<ImageData>
)