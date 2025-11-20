package com.mardillu.openai.network

import com.mardillu.openai.BuildConfig
import com.mardillu.openai.OpenAiConfig
import com.mardillu.openai.model.Message
import com.mardillu.openai.model.TextCompletionRequest
import com.mardillu.openai.model.requests.*
import com.mardillu.openai.model.response.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created on 26/03/2023 at 12:55 PM
 * @author mardillu
 */
class OpenApiClient(private val config: OpenAiConfig) {

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer ${config.apiKey}")
                .header("Content-Type", "application/json")
                .build()

            chain.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            addInterceptor(interceptor)
        }

        writeTimeout(config.timeout, config.timeUnit)
        readTimeout(config.timeout, config.timeUnit)
        connectTimeout(config.timeout, config.timeUnit)
    }
    .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(config.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val service: ChatGptApiService = retrofit.create(ChatGptApiService::class.java)

    /**
     * Helper function to get text completion.
     * For more advanced usage, use [service.getTextCompletion] directly.
     */
    suspend fun getTextCompletion(
            vararg prompt: String,
            model: String = "text-davinci-003",
            maxTokens: Int = 16,
            temperature: Double = 1.0,
            top_p: Double = 1.0,
            stream: Boolean = false,
            logprobs: Int? = null,
            stop: String? = null
    ): TextCompletionResponse {
        val requestBody = TextCompletionRequest(
                prompt,
                maxTokens,
                temperature,
                model,
                top_p,
                stream,
                logprobs,
                stop
        )
        return service.getTextCompletion(requestBody)
    }

    /**
     * Helper function to get chat completion.
     */
    suspend fun getChatCompletion(
            messages: List<Message>,
            model: String = "gpt-3.5-turbo"
    ): ChatCompletionResponse {
        val requestBody = ChatCompletionRequest(model, messages)
        return service.getChatCompletion(requestBody)
    }

    /**
     * Helper function to get edit completion.
     */
    suspend fun getEditCompletion(
            input: String,
            instruction: String,
            model: String = "text-davinci-edit-001"
    ): EditCompletionResponse {
        val requestBody = EditCompletionRequest(model, input, instruction)
        return service.getEditCompletion(requestBody)
    }

    /**
     * Helper function to get embeddings.
     */
    suspend fun getEmbeddings(
            input: String,
            model: String = "text-embedding-ada-002"
    ): CreateEmbeddingResponse {
        val requestBody = CreateEmbeddingRequest(model, input)
        return service.getEmbeddings(requestBody)
    }

    /**
     * Helper function to create image.
     */
    suspend fun createImage(
            prompt: String,
            n: Int = 2,
            size: String = "1024x1024"
    ): CreateImageResponse {
        if (n !in 1..10) {
            throw Exception("n (number of images to generate) must be between 1 and 10")
        }
        if (size !in listOf("1024x1024", "256x256", "512x512")) {
            throw Exception("The size of the images must be one of 256x256, 512x512, or 1024x1024")
        }

        val requestBody = CreateImageRequest(prompt, n, size)
        return service.createImage(requestBody)
    }

    /**
     * Helper function to get moderation.
     */
    suspend fun getModeration(
            input: String,
            model: String = "text-moderation-latest"
    ): ModerationResponse {
        val requestBody = ModerationRequest(input, model)
        return service.getModeration(requestBody)
    }

    /**
     * Helper function to get models.
     */
    suspend fun getModels(): GetModelsResponse {
        return service.getModels()
    }

    /**
     * Helper function to create image edit.
     */
    suspend fun createImageEdit(
            image: File,
            prompt: String,
            mask: File? = null,
            n: Int = 1,
            size: String = "1024x1024"
    ): CreateImageResponse {
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val _size = size.toRequestBody("text/plain".toMediaTypeOrNull())
        val _n = "$n".toRequestBody("text/plain".toMediaTypeOrNull())
        val _prompt = prompt.toRequestBody("text/plain".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        return if (mask != null) {
            val maskFile = mask.asRequestBody("image/*".toMediaTypeOrNull())
            val maskPart = MultipartBody.Part.createFormData("mask", mask.name, maskFile)

            service.createImageEdit(imagePart, maskPart, _prompt, _n, _size)
        } else {
            service.createImageEdit(imagePart, _prompt, _n, _size)
        }
    }

    /**
     * Helper function to create image variation.
     */
    suspend fun createImageVariation(
            image: File,
            n: Int = 1,
            size: String = "1024x1024"
    ): CreateImageResponse {
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val _size = size.toRequestBody("text/plain".toMediaTypeOrNull())
        val _n = "$n".toRequestBody("text/plain".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        return service.createImageVariation(imagePart, _n, _size)
    }

    /**
     * Helper function to create transcription.
     */
    suspend fun createTranscription(
            file: File,
            model: String = "whisper-1"
    ): SimpleTextResponse {
        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
        val _model = model.toRequestBody("text/plain".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        return service.createTranscription(audioPart, _model)
    }

    /**
     * Helper function to create translation.
     */
    suspend fun createTranslation(
            file: File,
            model: String = "whisper-1"
    ): SimpleTextResponse {
        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
        val _model = model.toRequestBody("text/plain".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        return service.createTranslation(audioPart, _model)
    }
}
