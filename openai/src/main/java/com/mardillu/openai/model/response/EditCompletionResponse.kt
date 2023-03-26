package com.mardillu.openai.model.response

data class EditCompletionResponse(
    var `object`: String,
    var created: Int,
    var choices: List<Choice>,
    var usage: Usage
)