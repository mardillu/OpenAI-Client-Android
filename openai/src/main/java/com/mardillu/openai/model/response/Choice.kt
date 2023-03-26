package com.mardillu.openai.model.response

data class Choice(
    var text: String,
    var index: Int,
    var logprobs: Any?,
    var finish_reason: String
)