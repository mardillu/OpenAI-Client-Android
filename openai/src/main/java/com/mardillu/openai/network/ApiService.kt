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
        @Deprecated("Use Chat Completions instead")
        @POST("completions")
        fun getTextCompletion(@Body request: TextCompletionRequest): Call<TextCompletionResponse>

        @POST("chat/completions")
        fun getChatCompletion(@Body request: ChatCompletionRequest): Call<ChatCompletionResponse>

        @Deprecated("Use Chat Completions instead")
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
                @Part("model") model: RequestBody,
                @Part("prompt") prompt: RequestBody? = null,
                @Part("response_format") responseFormat: RequestBody? = null,
                @Part("temperature") temperature: RequestBody? = null,
                @Part("language") language: RequestBody? = null
        ): Call<SimpleTextResponse>

        @Multipart
        @POST("audio/translations")
        fun createTranslation(
                @Part file: MultipartBody.Part,
                @Part("model") model: RequestBody,
                @Part("prompt") prompt: RequestBody? = null,
                @Part("response_format") responseFormat: RequestBody? = null,
                @Part("temperature") temperature: RequestBody? = null
        ): Call<SimpleTextResponse>

        // Files
        @GET("files")
        fun listFiles(): Call<ListFilesResponse>

        @Multipart
        @POST("files")
        fun uploadFile(
                @Part file: MultipartBody.Part,
                @Part("purpose") purpose: RequestBody
        ): Call<FileObject>

        @DELETE("files/{file_id}")
        fun deleteFile(@Path("file_id") fileId: String): Call<DeleteFileResponse>

        @GET("files/{file_id}")
        fun retrieveFile(@Path("file_id") fileId: String): Call<FileObject>

        @GET("files/{file_id}/content")
        fun retrieveFileContent(@Path("file_id") fileId: String): Call<ResponseBody>

        // Fine-tuning
        @POST("fine_tuning/jobs")
        fun createFineTuningJob(@Body request: CreateFineTuningJobRequest): Call<FineTuningJob>

        @GET("fine_tuning/jobs")
        fun listFineTuningJobs(
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): Call<ListFineTuningJobsResponse>

        @GET("fine_tuning/jobs/{fine_tuning_job_id}")
        fun retrieveFineTuningJob(@Path("fine_tuning_job_id") fineTuningJobId: String): Call<FineTuningJob>

        @POST("fine_tuning/jobs/{fine_tuning_job_id}/cancel")
        fun cancelFineTuningJob(@Path("fine_tuning_job_id") fineTuningJobId: String): Call<FineTuningJob>

        @GET("fine_tuning/jobs/{fine_tuning_job_id}/events")
        fun listFineTuningEvents(
                @Path("fine_tuning_job_id") fineTuningJobId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): Call<ListFineTuningEventsResponse>

        // Assistants
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("assistants")
        fun createAssistant(@Body request: CreateAssistantRequest): Call<Assistant>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("assistants")
        fun listAssistants(
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): Call<ListAssistantsResponse>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("assistants/{assistant_id}")
        fun retrieveAssistant(@Path("assistant_id") assistantId: String): Call<Assistant>

        // Threads
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads")
        fun createThread(@Body request: CreateThreadRequest): Call<Thread>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}")
        fun retrieveThread(@Path("thread_id") threadId: String): Call<Thread>

        @Headers("OpenAI-Beta: assistants=v2")
        @DELETE("threads/{thread_id}")
        fun deleteThread(@Path("thread_id") threadId: String): Call<DeleteFileResponse> // Reusing DeleteFileResponse as structure is same

        // Messages
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/messages")
        fun createMessage(
                @Path("thread_id") threadId: String,
                @Body request: CreateMessageRequest
        ): Call<ThreadMessage>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/messages")
        fun listMessages(
                @Path("thread_id") threadId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): Call<ListMessagesResponse>

        // Runs
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs")
        fun createRun(
                @Path("thread_id") threadId: String,
                @Body request: CreateRunRequest
        ): Call<Run>

        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs")
        @Streaming
        fun createRunStream(
                @Path("thread_id") threadId: String,
                @Body request: CreateRunRequest
        ): Call<ResponseBody>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/runs")
        fun listRuns(
                @Path("thread_id") threadId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): Call<ListRunsResponse>

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/runs/{run_id}")
        fun retrieveRun(
                @Path("thread_id") threadId: String,
                @Path("run_id") runId: String
        ): Call<Run>

        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs/{run_id}/cancel")
        fun cancelRun(
                @Path("thread_id") threadId: String,
                @Path("run_id") runId: String
        ): Call<Run>
}