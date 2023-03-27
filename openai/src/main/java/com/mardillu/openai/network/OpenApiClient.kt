package com.mardillu.openai.network

import com.mardillu.openai.BuildConfig
import com.mardillu.openai.OpenAiInitializer
import com.mardillu.openai.model.*
import com.mardillu.openai.model.requests.*
import com.mardillu.openai.model.response.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created on 26/03/2023 at 12:55 PM
 * @author mardillu
 */
class OpenApiClient {
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer ${OpenAiInitializer.CHAT_GPT_API_KEY}")
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

        writeTimeout(1, TimeUnit.MINUTES)
        readTimeout(1, TimeUnit.MINUTES)
        connectTimeout(1, TimeUnit.MINUTES)
    }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    private val apiService = retrofit.create(ChatGptApiService::class.java)

    /**
     * Given a prompt, the model will return one or more predicted completions, a
     * nd can also return the probabilities of alternative tokens at each position.
     *
     * @param prompt String The prompt(s) to generate completions for, encoded as a string or array of strings.
     * @param model String ID of the model to use. You can use the List models API, {@link ChatGptService#getModels()}, to see all of your available models,
     *          or see our Model overview for descriptions of them
     * @param maxTokens Int The maximum number of tokens to generate in the completion.
     * @param temperature Double What sampling temperature to use, between 0 and 2.
     *          Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     * @param top_p Double An alternative to sampling with temperature, called nucleus sampling,
     *          where the model considers the results of the tokens with top_p probability mass.
     *          So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * @param stream Boolean Whether to stream back partial progress.
     *          If set, tokens will be sent as data-only server-sent events as they become available,
     *          with the stream terminated by a data: [DONE] message.
     * @param logprobs Int? Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens.
     *          For example, if logprobs is 5, the API will return a list of the 5 most likely tokens.
     *          The API will always return the logprob of the sampled token, so there may be up to logprobs+1 elements in the response.
     * @param stop String? Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop sequence.
     * @param completionHandler Function2<TextCompletionResponse?, Throwable?, Unit> Callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/completions">OpenAI API Reference</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getTextCompletion(
        vararg prompt: String,
        model: String = "text-davinci-003",
        maxTokens: Int = 16,
        temperature: Double = 1.0,
        top_p: Double = 1.0,
        stream: Boolean = false,
        logprobs: Int? = null,
        stop: String? = null,
        completionHandler: (TextCompletionResponse?, Throwable?) -> Unit
    ) {
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
        val call = apiService.getTextCompletion(requestBody)

        call.enqueue(object : Callback<TextCompletionResponse> {
            override fun onResponse(
                call: Call<TextCompletionResponse>,
                response: Response<TextCompletionResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<TextCompletionResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Given a chat conversation, the model will return a chat completion response.
     *
     * @param messages List<Message>
     * @param model String ID of the model to use. See the model endpoint compatibility table for details on which models work with the Chat API.
     * @param completionHandler Function2<ChatCompletionResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/chat">OpenAI API Reference for Chat</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getChatCompletion(
        messages: List<Message>,
        model: String = "gpt-3.5-turbo",
        completionHandler: (ChatCompletionResponse?, Throwable?) -> Unit
    ) {
        val requestBody = ChatCompletionRequest(model, messages)
        val call = apiService.getChatCompletion(requestBody)

        call.enqueue(object : Callback<ChatCompletionResponse> {
            override fun onResponse(
                call: Call<ChatCompletionResponse>,
                response: Response<ChatCompletionResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<ChatCompletionResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Given a prompt and an instruction, the model will return an edited version of the prompt.
     *
     * @param input String The input text to use as a starting point for the edit.
     * @param instruction String The instruction that tells the model how to edit the prompt.
     * @param model ID of the model to use. You can use the text-davinci-edit-001 or code-davinci-edit-001 model with this endpoint.
     * @param completionHandler Function2<EditCompletionResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/edits">OpenAI API Reference for Edits</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getEditCompletion(
        input: String,
        instruction: String,
        model: String = "text-davinci-edit-001",
        completionHandler: (EditCompletionResponse?, Throwable?) -> Unit
    ) {
        val requestBody = EditCompletionRequest(model, input, instruction)
        val call = apiService.getEditCompletion(requestBody)

        call.enqueue(object : Callback<EditCompletionResponse> {
            override fun onResponse(
                call: Call<EditCompletionResponse>,
                response: Response<EditCompletionResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<EditCompletionResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    private fun getEditCompletionAltInternal(
        vararg prompt: String,
        model: String = "text-davinci-003",
        maxTokens: Int = 16,
        temperature: Double = 1.0,
        top_p: Double = 1.0,
        stream: Boolean = false,
        logprobs: Int? = null,
        stop: String? = null,
        completionHandler: (TextCompletionResponse?, Throwable?) -> Unit
    ) {
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
        val call = apiService.getTextCompletion(requestBody)

        call.enqueue(object : Callback<TextCompletionResponse> {
            override fun onResponse(
                call: Call<TextCompletionResponse>,
                response: Response<TextCompletionResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<TextCompletionResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Given a prompt and an instruction, the model will return an edited version of the prompt.
     * <p> This implementation uses the completion api as a workaround to an age-long issue with the
     * actual Edit api
     *
     * @param input String The input text to use as a starting point for the edit.
     * @param instruction String The instruction that tells the model how to edit the prompt.
     * @param completionHandler Function2<EditCompletionResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/edits">OpenAI API Reference for Edits</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getEditCompletionAlt(input: String,
                             instruction: String,
                             completionHandler: (TextCompletionResponse?, Throwable?) -> Unit
    ){
        getEditCompletionAltInternal("$input. $instruction",) { response, t ->
            if (response == null){
                completionHandler(null, t)
            } else {
                completionHandler(response, null)
            }
        }
    }

    /**
     *
     * @param input String Input text to get embeddings for, encoded as a string or array of tokens.
     *          To get embeddings for multiple inputs in a single request, pass an array of strings or array of token arrays.
     *          Each input must not exceed 8192 tokens in length.
     * @param model String String String ID of the model to use. See the model endpoint compatibility table for details on which models work with the Chat API.
     * @param completionHandler Function2<CreateEmbeddingResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/embeddings">OpenAI API Reference for Embeddings</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getEmbeddings(
        input: String,
        model: String = "text-embedding-ada-002",
        completionHandler: (CreateEmbeddingResponse?, Throwable?) -> Unit
    ) {
        val requestBody = CreateEmbeddingRequest(model, input)
        val call = apiService.getEmbeddings(requestBody)

        call.enqueue(object : Callback<CreateEmbeddingResponse> {
            override fun onResponse(
                call: Call<CreateEmbeddingResponse>,
                response: Response<CreateEmbeddingResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<CreateEmbeddingResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Creates an image given a prompt.
     *
     * @param prompt String A text description of the desired image(s). The maximum length is 1000 characters.
     * @param n Int The number of images to generate. Must be between 1 and 10.
     * @param size String The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     * @param completionHandler Function2<CreateImageResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/images/create">OpenAI API Reference for creating images</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun createImage(
        prompt: String,
        n: Int = 2,
        size: String = "1024x1024",
        completionHandler: (CreateImageResponse?, Throwable?) -> Unit
    ) {
        if (n !in 1..10){
            throw Exception("n (number of images to generate) must be between 1 and 10")
        }
        if (size !in listOf("1024x1024", "256x256", "512x512")){
            throw Exception("The size of the images must be one of 256x256, 512x512, or 1024x1024")
        }

        val requestBody = CreateImageRequest(prompt, n, size)
        val call = apiService.createImage(requestBody)

        call.enqueue(object : Callback<CreateImageResponse> {
            override fun onResponse(
                call: Call<CreateImageResponse>,
                response: Response<CreateImageResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<CreateImageResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Given a input text, outputs if the model classifies it as violating OpenAI's content policy.
     *
     * @param input String The input text to classify
     * @param model Two content moderations models are available: text-moderation-stable and text-moderation-latest.
     * @param completionHandler Function2<ModerationResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/moderations">OpenAI API Reference for Moderation</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getModeration(
        input: String,
        model: String = "text-moderation-latest",
        completionHandler: (ModerationResponse?, Throwable?) -> Unit
    ) {
        val requestBody = ModerationRequest(input, model)
        val call = apiService.getModeration(requestBody)

        call.enqueue(object : Callback<ModerationResponse> {
            override fun onResponse(
                call: Call<ModerationResponse>,
                response: Response<ModerationResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<ModerationResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Lists the currently available models, and provides basic information about each one such as the owner and availability.
     *
     * @param completionHandler Function2<GetModelsResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/models/list">OpenAI API Reference for Models</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun getModels(
        completionHandler: (GetModelsResponse?, Throwable?) -> Unit
    ) {
        val call = apiService.getModels()

        call.enqueue(object : Callback<GetModelsResponse> {
            override fun onResponse(
                call: Call<GetModelsResponse>,
                response: Response<GetModelsResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<GetModelsResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Creates an edited or extended image given an original image and a prompt.
     *
     * @param image The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided, image must have transparency, which will be used as the mask.
     * @param prompt A text description of the desired image(s). The maximum length is 1000 characters.
     * @param mask An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     * @param n The number of images to generate. Must be between 1 and 10.
     * @param size The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     * @param completionHandler Function2<GetModelsResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/images/create-edit">OpenAI API Reference for Image Edit</a>
     * @see {@link ChatGptService#getModels()}
     */
    fun createImageEdit(
            image: File,
            prompt: String,
            mask: File? = null,
            n: Int = 1,
            size: String = "1024x1024",
            completionHandler: (CreateImageResponse?, Throwable?) -> Unit
    ) {
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val _size = size.toRequestBody("text/plain".toMediaTypeOrNull())
        val _n = "$n".toRequestBody("text/plain".toMediaTypeOrNull())
        val _prompt = prompt.toRequestBody("text/plain".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        val call = if (mask != null) {
            val maskFile = mask.asRequestBody("image/*".toMediaTypeOrNull())
            val maskPart = MultipartBody.Part.createFormData("mask", mask.name, maskFile)

            apiService.createImageEdit(imagePart, maskPart, _prompt, _n, _size)
        } else {
            apiService.createImageEdit(imagePart, _prompt, _n, _size)
        }

        call.enqueue(object : Callback<CreateImageResponse> {
            override fun onResponse(
                    call: Call<CreateImageResponse>,
                    response: Response<CreateImageResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val err = response.errorBody()?.string()
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<CreateImageResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Creates a variation of a given image.
     *
     * @param image The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     * @param n The number of images to generate. Must be between 1 and 10.
     * @param size The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     * @param completionHandler Function2<GetModelsResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/images/create-variation">OpenAI API Reference for Image Variations</a>
     */
    fun createImageVariation(
            image: File,
            n: Int = 1,
            size: String = "1024x1024",
            completionHandler: (CreateImageResponse?, Throwable?) -> Unit
    ) {
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val _size = size.toRequestBody("text/plain".toMediaTypeOrNull())
        val _n = "$n".toRequestBody("text/plain".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        val call = apiService.createImageVariation(imagePart, _n, _size)

        call.enqueue(object : Callback<CreateImageResponse> {
            override fun onResponse(
                    call: Call<CreateImageResponse>,
                    response: Response<CreateImageResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<CreateImageResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Transcribes audio into the input language.
     *
     * @param file The audio file to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
     * @param model ID of the model to use. Only whisper-1 is currently available.
     * @param completionHandler Function2<GetModelsResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/images/create-variation">OpenAI API Reference for Image Variations</a>
     */
    fun createTranscription(
            file: File,
            model: String = "whisper-1",
            completionHandler: (SimpleTextResponse?, Throwable?) -> Unit
    ) {
        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
        val _model = model.toRequestBody("text/plain".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val call = apiService.createTranscription(audioPart, _model)

        call.enqueue(object : Callback<SimpleTextResponse> {
            override fun onResponse(
                    call: Call<SimpleTextResponse>,
                    response: Response<SimpleTextResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val err = response.errorBody()?.string()
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<SimpleTextResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

    /**
     * Transcribes audio into the input language.
     *
     * @param file The audio file to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
     * @param model ID of the model to use. Only whisper-1 is currently available.
     * @param completionHandler Function2<GetModelsResponse?, Throwable?, Unit> callback handler
     * @see <a href="https://platform.openai.com/docs/api-reference/images/create-variation">OpenAI API Reference for Image Variations</a>
     */
    fun createTranslation(
            file: File,
            model: String = "whisper-1",
            completionHandler: (SimpleTextResponse?, Throwable?) -> Unit
    ) {
        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
        val _model = model.toRequestBody("text/plain".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val call = apiService.createTranslation(audioPart, _model)

        call.enqueue(object : Callback<SimpleTextResponse> {
            override fun onResponse(
                    call: Call<SimpleTextResponse>,
                    response: Response<SimpleTextResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val err = response.errorBody()?.string()
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<SimpleTextResponse>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }
}


