package com.mardillu.openai.model.response

data class FineTuningEvent(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val level: String,
    val message: String,
    val type: String? = null,
    val data: Any? = null
)
