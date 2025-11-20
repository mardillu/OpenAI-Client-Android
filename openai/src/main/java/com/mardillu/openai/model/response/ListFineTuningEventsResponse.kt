package com.mardillu.openai.model.response

data class ListFineTuningEventsResponse(
    val `object`: String,
    val data: List<FineTuningEvent>,
    val has_more: Boolean
)
