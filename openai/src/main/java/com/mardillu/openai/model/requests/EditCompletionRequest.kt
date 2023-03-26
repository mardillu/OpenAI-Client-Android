package com.mardillu.openai.model.requests

data class EditCompletionRequest(
    var model: String,
    var input: String,
    var instruction: String
)