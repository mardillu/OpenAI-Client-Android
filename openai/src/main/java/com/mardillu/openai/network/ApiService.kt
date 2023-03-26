package com.mardillu.openai.network

import com.mardillu.openai.model.*
import com.mardillu.openai.model.requests.*
import com.mardillu.openai.model.response.*
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

        @POST("models")
        fun getModels(): Call<GetModelsResponse>
}