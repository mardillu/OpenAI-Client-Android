# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Released]

### Added
- **Assistants API (Beta)** - Full support for OpenAI Assistants v2
  - Create, retrieve, and list Assistants
  - Thread management (create, retrieve, delete)
  - Message operations within threads
  - Run operations with streaming support
  - Vector Store support via `tool_resources`
  - File Search and Code Interpreter v2 capabilities
- **Files API** - Upload, list, retrieve, and delete files
- **Fine-tuning API** - Create and manage fine-tuning jobs
- **Streaming for Assistants** - Server-Sent Events (SSE) support via `createRunStream`
- **Vector Store Support** - New models for `ToolResources`, `FileSearchResources`, and `VectorStore`
- **Coroutines Support** - All API methods now use `suspend` functions
- **OpenAiConfig** - Configuration class for API key and client settings

### Changed
- **BREAKING**: Replaced callback-based API with Kotlin Coroutines
  - All methods now use `suspend` functions instead of callbacks
  - Error handling via try-catch instead of error callbacks
- **BREAKING**: Removed `OpenAiInitializer` static initialization
  - Use `OpenAiConfig` constructor parameter instead
- **Chat Completions** - Updated to support GPT-4 and GPT-3.5 Turbo features
  - Function calling / Tools support
  - JSON mode via `response_format`
  - Added `seed`, `system_fingerprint`, and other modern parameters
- **Image Generation** - Updated for DALL-E 3 support
  - Added `model`, `quality`, and `style` parameters
- **Audio API** - Enhanced transcription and translation endpoints
  - Added optional parameters: `prompt`, `response_format`, `temperature`, `language`
- **Tool Definition** - Made `function` nullable to support `file_search` and `code_interpreter` types
- **Moderation API** - Updated default model to `omni-moderation-latest`

### Deprecated
- **Completions API** - Legacy text completions (use Chat Completions instead)
- **Edits API** - Legacy edits endpoint (use Chat Completions instead)
- `file_ids` in `CreateAssistantRequest` - Use `tool_resources` for Assistants v2

### Updated
- Gradle Wrapper to 8.6
- Android Gradle Plugin to 8.3.1
- Kotlin to 1.9.23
- AndroidX Core KTX to 1.12.0
- Material Components to 1.11.0
- Retrofit to 2.9.0
- OkHttp Logging Interceptor to 4.12.0
- Added lifecycle-runtime-ktx 2.6.2 for Coroutines support

### Fixed
- Import issues in various model files
- Compilation errors related to deprecated APIs

## Migration Guide

### From Callbacks to Coroutines

**Before:**
```kotlin
OpenAiInitializer.initialize("API_KEY")
val client = OpenApiClient()

client.getChatCompletion(messages) { result, error ->
    if (error != null) {
        // Handle error
    } else if (result != null) {
        // Use result
    }
}
```

**After:**
```kotlin
val config = OpenAiConfig(apiKey = "API_KEY")
val client = OpenApiClient(config)

lifecycleScope.launch {
    try {
        val result = client.getChatCompletion(messages)
        // Use result
    } catch (e: Exception) {
        // Handle error
    }
}
```

### Using Vector Stores

```kotlin
val assistant = CreateAssistantRequest(
    model = "gpt-4",
    tools = listOf(Tool(type = "file_search")),
    tool_resources = ToolResources(
        file_search = FileSearchResources(
            vector_store_ids = listOf("vs_123")
        )
    )
)
```
