package com.mardillu.openai.model.response

data class DeleteFileResponse(
    val id: String,
    val `object`: String,
    val deleted: Boolean
)
