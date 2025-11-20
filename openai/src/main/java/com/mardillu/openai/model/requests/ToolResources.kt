package com.mardillu.openai.model.requests

data class ToolResources(
    val code_interpreter: CodeInterpreterResources? = null,
    val file_search: FileSearchResources? = null
)

data class CodeInterpreterResources(
    val file_ids: List<String> = emptyList()
)

data class FileSearchResources(
    val vector_store_ids: List<String> = emptyList(),
    val vector_stores: List<VectorStore>? = null
)

data class VectorStore(
    val file_ids: List<String> = emptyList(),
    val chunking_strategy: VectorStoreChunkingStrategy? = null,
    val metadata: Map<String, String>? = null
)

data class VectorStoreChunkingStrategy(
    val type: String,
    val static: StaticChunkingStrategy? = null
)

data class StaticChunkingStrategy(
    val max_chunk_size_tokens: Int,
    val chunk_overlap_tokens: Int
)
