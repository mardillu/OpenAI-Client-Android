package com.mardillu.openai.model.requests

data class LogApiRequest(
    val route: String,
    val request_time: Long,
    val response_time: Long,
    val duration: Long,
    val response_code: Int,
    val method: String,
    val accountName: String = "NA",
    val userId: Long = 0L,
    val exception: String = "",
)