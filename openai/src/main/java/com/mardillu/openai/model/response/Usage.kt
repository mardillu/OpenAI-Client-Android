package com.mardillu.openai.model.response

/**
 * Created on 26/03/2023 at 2:06 PM
 * @author mardillu
 */
data class Usage(
    var prompt_tokens: Int,
    var completion_tokens: Int?,
    var total_tokens: Int
)
