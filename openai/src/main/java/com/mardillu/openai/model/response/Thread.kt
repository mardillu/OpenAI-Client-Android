package com.mardillu.openai.model.response

data class Thread(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val metadata: Map<String, String>
)
