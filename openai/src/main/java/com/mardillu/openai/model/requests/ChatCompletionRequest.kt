package com.mardillu.openai.model.requests

import com.mardillu.openai.model.Message

data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>,
    val frequency_penalty: Double = 0.0,
    val logit_bias: Map<String, Int>? = null,
    val logprobs: Boolean = false,
    val top_logprobs: Int? = null,
    val max_tokens: Int? = null,
    val n: Int = 1,
    val presence_penalty: Double = 0.0,
    val response_format: ResponseFormat? = null,
    val seed: Int? = null,
    val stop: Any? = null, // String or Array of Strings
    val stream: Boolean = false,
    val temperature: Double = 1.0,
    val top_p: Double = 1.0,
    val tools: List<Tool>? = null,
    val tool_choice: Any? = null, // String or ToolChoice object
    val user: String? = null
)

data class ResponseFormat(
    val type: String
)

data class Tool(
    val type: String,
    val function: FunctionDefinition
)

data class FunctionDefinition(
    val name: String,
    val description: String? = null,
    val parameters: Any? = null // JSON Schema object
)