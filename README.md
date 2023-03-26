# OpenAPI Client for Android
A light weight OpenAI's API client for Android. This tool is specifically designed for Android developers to interact with OpenAI's API in a lightweight and efficient manner. With this client, Android developers will be able to access OpenAI's services seamlessly, without having to worry about heavy resource consumption or slow performance. This tool promises to be an invaluable asset for Android developers and users who require quick and easy access to OpenAI's API.

## Disclaimer
OpenAPI Client for Android is an open-sourced software licensed under the  [MIT license](LICENSE.md).  **This is an unofficial library, it is not affiliated with nor endorsed by OpenAI**. Contributions are welcome.

## Setup
Install OpenAI API Android client by adding the following dependency to your  `gradle.build`  file:
~~~groovy
repositories {
	maven { url 'https://jitpack.io' }
}

dependencies {
	implementation 'com.github.mardillu:multiscanner:15beeaeb'
}
~~~

## Getting started
1. Initialize the client by calling the following function. You only need to do this once, ideally, in your Application class using environment variables for the API_KEY.
~~~kotlin
OPenAiInitializer.initialize("API_KEY")
~~~
2. Create an instance of `OpenAiClient`
```kotlin
val client = OpenApiClient()
```
3. Make a request
```kotlin
client.getTextCompletion("Hello chat gpt! what is the meaning of life?") { result, error ->
	if (error != null) {
		// Handle error
	} else if (result != null) {
		Log.d("TAG", result.choices[0].text)
	}
}
```
## Supported APIs
- [x] Completions
- [x] Chat
- [x] Edits
- [x] Image Generate
- [x] Embeddings
- [x] Moderations
- [x] Models

## Upcoming APIs
- [ ] Audio
- [ ] Fine-tunes
- [ ] Image Edit
- [ ] Image variatation
- [ ] Files

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