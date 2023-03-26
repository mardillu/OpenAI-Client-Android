package com.mardillu.openai.model.response

data class ModerationResponse(
    var id: String,
    var model: String,
    var results: List<Result>
)