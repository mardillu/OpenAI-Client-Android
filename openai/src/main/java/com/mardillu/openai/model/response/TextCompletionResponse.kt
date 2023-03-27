package com.mardillu.openai.model.response

/**
 * Created on 26/03/2023 at 12:46 PM
 * @author mardillu
 */
data class TextCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Int,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)