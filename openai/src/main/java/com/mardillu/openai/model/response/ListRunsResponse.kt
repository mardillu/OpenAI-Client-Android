package com.mardillu.openai.model.response

data class ListRunsResponse(
    val `object`: String,
    val data: List<Run>,
    val first_id: String? = null,
    val last_id: String? = null,
    val has_more: Boolean
)
