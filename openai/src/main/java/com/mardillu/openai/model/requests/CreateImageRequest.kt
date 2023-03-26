package com.mardillu.openai.model.requests

data class CreateImageRequest(
    var prompt: String,
    var n: Int,
    var size: String
)