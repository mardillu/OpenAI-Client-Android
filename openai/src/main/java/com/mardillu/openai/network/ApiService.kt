package com.mardillu.openai.network

import com.mardillu.openai.model.*
import com.mardillu.openai.model.requests.*
import com.mardillu.openai.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created on 26/03/2023 at 12:29 PM
 * @author mardillu
 */
interface ChatGptApiService {
        @POST("completions")
        fun getTextCompletion(@Body request: TextCompletionRequest): Call<TextCompletionResponse>

        @POST("chat/completions")
        fun getChatCompletion(@Body request: ChatCompletionRequest): Call<ChatCompletionResponse>

        @POST("edits")
        fun getEditCompletion(@Body request: EditCompletionRequest): Call<EditCompletionResponse>

        @POST("embeddings")
        fun getEmbeddings(@Body request: CreateEmbeddingRequest): Call<CreateEmbeddingResponse>

        @POST("images/generations")
        fun createImage(@Body request: CreateImageRequest): Call<CreateImageResponse>

        @POST("moderations")
        fun getModeration(@Body request: ModerationRequest): Call<ModerationResponse>

        @GET("models")
        fun getModels(): Call<GetModelsResponse>

        @Multipart
        @POST("images/edits")
        fun createImageEdit(
                @Part image: MultipartBody.Part,
                @Part mask: MultipartBody.Part,
                @Part("prompt") prompt: RequestBody,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): Call<CreateImageResponse>

        @Multipart
        @POST("images/edits")
        fun createImageEdit(
                @Part image: MultipartBody.Part,
                @Part("prompt") prompt: RequestBody,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): Call<CreateImageResponse>

        @Multipart
        @POST("images/variations")
        fun createImageVariation(
                @Part image: MultipartBody.Part,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): Call<CreateImageResponse>

        @Multipart
        @POST("audio/transcriptions")
        fun createTranscription(
                @Part file: MultipartBody.Part,
                @Part("model") size: RequestBody
        ): Call<SimpleTextResponse>

        @Multipart
        @POST("audio/translations")
        fun createTranslation(
                @Part file: MultipartBody.Part,
                @Part("model") size: RequestBody
        ): Call<SimpleTextResponse>
}