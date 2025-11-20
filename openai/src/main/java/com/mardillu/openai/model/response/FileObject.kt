package com.mardillu.openai.model.response

data class FileObject(
    val id: String,
    val bytes: Long,
    val created_at: Long,
    val filename: String,
    val `object`: String,
    val purpose: String,
    val status: String,
    val status_details: String? = null
)
