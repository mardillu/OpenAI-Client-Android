package com.mardillu.openai.model.response

data class Data(
    val `object`: String,
    val embedding: List<Double>,
    val index: Int
)