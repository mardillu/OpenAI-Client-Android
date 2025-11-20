package com.mardillu.openai.model.response

import com.mardillu.openai.model.requests.Tool

data class Assistant(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val name: String? = null,
    val description: String? = null,
    val model: String,
    val instructions: String? = null,
    val tools: List<Tool>,
    val file_ids: List<String>,
    val metadata: Map<String, String>
)
