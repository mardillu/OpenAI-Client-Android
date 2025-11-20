package com.mardillu.openai.model

data class Message(
    val role: String,
    val content: String? = null,
    val name: String? = null,
    val tool_calls: List<ToolCall>? = null,
    val tool_call_id: String? = null
)

data class ToolCall(
    val id: String,
    val type: String,
    val function: FunctionCall
)

data class FunctionCall(
    val name: String,
    val arguments: String
)