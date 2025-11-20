package com.mardillu.openai.model.requests

data class CreateRunRequest(
    val assistant_id: String,
    val model: String? = null,
    val instructions: String? = null,
    val tools: List<Tool>? = null,
    val metadata: Map<String, String>? = null,
    val stream: Boolean? = null
)
