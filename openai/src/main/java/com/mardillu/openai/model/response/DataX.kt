package com.mardillu.openai.model.response

data class DataX(
    var id: String,
    var `object`: String,
    var owned_by: String,
    var permission: List<Any>
)