package com.mardillu.openai.model.response

/**
 * Created on 26/03/2023 at 2:06 PM
 * @author mardillu
 */
data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int?,
    val total_tokens: Int
)
