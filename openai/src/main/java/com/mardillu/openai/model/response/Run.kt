package com.mardillu.openai.model.response

import com.mardillu.openai.model.requests.Tool
import com.mardillu.openai.model.ToolCall

data class Run(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val thread_id: String,
    val assistant_id: String,
    val status: String,
    val required_action: RequiredAction? = null,
    val last_error: LastError? = null,
    val expires_at: Long? = null,
    val started_at: Long? = null,
    val cancelled_at: Long? = null,
    val failed_at: Long? = null,
    val completed_at: Long? = null,
    val model: String,
    val instructions: String? = null,
    val tools: List<Tool>,
    val file_ids: List<String>,
    val metadata: Map<String, String>
)

data class RequiredAction(
    val type: String,
    val submit_tool_outputs: SubmitToolOutputs? = null
)

data class SubmitToolOutputs(
    val tool_calls: List<ToolCall>
)

data class LastError(
    val code: String,
    val message: String
)
