package com.mardillu.openai.model.response

data class ListFineTuningJobsResponse(
    val `object`: String,
    val data: List<FineTuningJob>,
    val has_more: Boolean
)
