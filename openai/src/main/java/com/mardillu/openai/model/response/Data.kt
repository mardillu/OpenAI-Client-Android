package com.mardillu.openai.model.response

data class Data(
    var `object`: String,
    var embedding: List<Double>,
    var index: Int
)