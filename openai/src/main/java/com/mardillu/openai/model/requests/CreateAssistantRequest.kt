package com.mardillu.openai.model.requests

import com.mardillu.openai.model.requests.Tool

data class CreateAssistantRequest(
    val model: String,
    val name: String? = null,
    val description: String? = null,
    val instructions: String? = null,
    val tools: List<Tool>? = null,
    val file_ids: List<String>? = null,
    val metadata: Map<String, String>? = null,
    val tool_resources: ToolResources? = null
)
