package com.mardillu.openai.model.response

data class DataX(
    val id: String,
    val `object`: String,
    val owned_by: String,
    val permission: List<Any>
)