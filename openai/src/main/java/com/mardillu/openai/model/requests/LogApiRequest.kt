package com.mardillu.openai.model.requests

data class LogApiRequest(
    val route: String,
    val request_time: Int,
    val response_time: Int,
    val duration: Int,
    val response_code: Int,
)