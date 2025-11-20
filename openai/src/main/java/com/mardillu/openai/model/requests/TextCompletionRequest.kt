package com.mardillu.openai.model

/**
 * Created on 26/03/2023 at 12:35 PM
 * @author mardillu
 */
@Deprecated("Use ChatCompletionRequest instead")
data class TextCompletionRequest(
    val prompt: Array<out String>,
    val max_tokens: Int = 16,
    val temperature: Double = 1.0,
    val model: String?,
    val top_p: Double = 1.0,
    val stream: Boolean = false,
    val logprobs: Int? = null,
    val stop: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextCompletionRequest

        if (!prompt.contentEquals(other.prompt)) return false
        if (max_tokens != other.max_tokens) return false
        if (temperature != other.temperature) return false
        if (model != other.model) return false
        if (top_p != other.top_p) return false
        if (stream != other.stream) return false
        if (logprobs != other.logprobs) return false
        if (stop != other.stop) return false

        return true
    }

    override fun hashCode(): Int {
        var result = prompt.contentHashCode()
        result = 31 * result + max_tokens
        result = 31 * result + temperature.hashCode()
        result = 31 * result + (model?.hashCode() ?: 0)
        result = 31 * result + top_p.hashCode()
        result = 31 * result + stream.hashCode()
        result = 31 * result + (logprobs ?: 0)
        result = 31 * result + (stop?.hashCode() ?: 0)
        return result
    }
}

