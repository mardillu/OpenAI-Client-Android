package com.mardillu.openai.model.response

data class ThreadMessage(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val thread_id: String,
    val role: String,
    val content: List<MessageContent>,
    val assistant_id: String? = null,
    val run_id: String? = null,
    val file_ids: List<String>,
    val metadata: Map<String, String>
)

data class MessageContent(
    val type: String,
    val text: MessageText? = null,
    val image_file: MessageImageFile? = null
)

data class MessageText(
    val value: String,
    val annotations: List<Any>
)

data class MessageImageFile(
    val file_id: String
)
