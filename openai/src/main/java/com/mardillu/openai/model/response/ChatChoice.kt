package com.mardillu.openai.model.response

import com.mardillu.openai.model.Message

data class ChatChoice(
    var index: Int,
    var finish_reason: String,
    val message: Message,
)