package com.mardillu.openai.model.response

data class CreateImageResponse(
    var created: Int,
    var `data`: List<ImageData>
)