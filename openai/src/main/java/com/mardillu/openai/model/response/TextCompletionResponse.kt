package com.mardillu.openai.model.response

/**
 * Created on 26/03/2023 at 12:46 PM
 * @author mardillu
 */
data class TextCompletionResponse(
    var id: String,
    var `object`: String,
    var created: Int,
    var model: String,
    var choices: List<Choice>,
    var usage: Usage
)