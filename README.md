# OpenAI Client for Android
A lightweight OpenAI API client for Android. This tool is specifically designed for Android developers to interact with OpenAI's API in a lightweight and efficient manner. With this client, Android developers will be able to access OpenAI's services seamlessly, without having to worry about heavy resource consumption or slow performance. This tool promises to be an invaluable asset for Android developers and users who require quick and easy access to OpenAI's API.

[![Jitpack](https://jitpack.io/v/mardillu/OpenAI-Client-Android.svg)](https://jitpack.io/#mardillu/OpenAI-Client-Android)
[![Build & Unit Test](https://github.com/mardillu/OpenAI-Client-Android/actions/workflows/build.yml/badge.svg)](https://github.com/mardillu/OpenAI-Client-Android/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/Aallam/openai-kotlin?color=yellow)](LICENSE.md)


## Disclaimer
OpenAI Client for Android is an open-sourced software licensed under the [MIT license](https://github.com/mardillu/OpenAI-Client-Android/blob/master/LICENSE).  **This is an unofficial library, it is not affiliated with nor endorsed by OpenAI**. Contributions are welcome.

## Setup
1. Add this to your Project level `build.gradle` at the end of repositories:
~~~groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
~~~
2. Add the dependency to your module level `build.gradle`:
~~~groovy
dependencies {
    //replace XX with the latest version
    implementation 'com.github.mardillu:OpenAI-Client-Android:XX'
}
~~~

## Getting started
1. Create an instance of `OpenAiConfig` and `OpenApiClient`:
```kotlin
val config = OpenAiConfig(apiKey = "YOUR_API_KEY")
val client = OpenApiClient(config)
```
2. Make a request using Coroutines:
```kotlin
lifecycleScope.launch {
    try {
        val result = client.getTextCompletion("Hello chat gpt! what is the meaning of life?")
        Log.d("TAG", result.choices[0].text)
    } catch (e: Exception) {
        // Handle error
        e.printStackTrace()
    }
}
```
## Supported APIs
- [x] Chat Completions (GPT-4, GPT-3.5)
  - [x] Function Calling / Tools
  - [x] JSON Mode
- [x] Images (DALL-E 3, DALL-E 2)
  - Create
  - Edit
  - Variations
- [x] Embeddings
- [x] Moderations
- [x] Models
- [x] Audio
  - Transcribe
  - Translate
- [x] Assistants (Beta)
- [x] Threads (Beta)
- [x] Runs (Beta)
- [x] Messages (Beta)
- [x] Files
- [x] Fine-tuning
- [x] Completions (Legacy)
- [x] Edits (Legacy)
- [x] Streaming for Assistants

## Roadmap

### Upcoming Features
- [ ] **Streaming for Chat Completions** - Real-time token streaming for chat responses
- [ ] **Batch API** - Support for asynchronous batch processing
- [ ] **Vector Stores API** - Direct vector store management endpoints
- [ ] **Uploads API** - Large file upload support
- [ ] **Realtime API** - WebSocket-based real-time communication (GPT-4o Realtime)

### Enhancements
- [ ] **Response Caching** - Built-in caching mechanism for API responses
- [ ] **Retry Logic** - Automatic retry with exponential backoff
- [ ] **Rate Limiting** - Client-side rate limit handling
- [ ] **Modularization** - Split client into focused modules (e.g., `client.chat`, `client.images`, `client.assistants`)
- [ ] **Comprehensive Testing** - Unit and integration tests for all endpoints
- [ ] **Sample App** - Full-featured demo application showcasing all capabilities

### Documentation
- [ ] **API Documentation** - KDoc documentation for all public APIs
- [ ] **Usage Examples** - Comprehensive examples for each endpoint
- [ ] **Migration Guide** - Detailed guide for upgrading from legacy versions

Want to contribute? Check out our [issues](https://github.com/mardillu/OpenAI-Client-Android/issues) or submit a PR!


## License
MIT License

Copyright (c) 2023 Ezekiel Sebastine

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.