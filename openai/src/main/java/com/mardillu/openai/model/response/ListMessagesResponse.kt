package com.mardillu.openai.model.response

data class ListMessagesResponse(
    val `object`: String,
    val data: List<ThreadMessage>,
    val first_id: String? = null,
    val last_id: String? = null,
    val has_more: Boolean
)
