package com.mardillu.openai.model.response

data class ListAssistantsResponse(
    val `object`: String,
    val data: List<Assistant>,
    val first_id: String? = null,
    val last_id: String? = null,
    val has_more: Boolean
)
