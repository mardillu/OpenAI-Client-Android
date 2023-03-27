package com.mardillu.openai.model

data class OpenAiError(
    val error: Error?
)

data class Error(
    val message: String?,
    val type: String?,
    val `param`: String?,
    val code: String?
)