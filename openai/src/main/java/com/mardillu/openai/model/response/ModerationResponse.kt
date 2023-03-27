package com.mardillu.openai.model.response

data class ModerationResponse(
    val id: String,
    val model: String,
    val results: List<Result>
)