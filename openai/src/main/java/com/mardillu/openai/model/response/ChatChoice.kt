package com.mardillu.openai.model.response

import com.mardillu.openai.model.Message

data class ChatChoice(
    val index: Int,
    val finish_reason: String,
    val message: Message,
)