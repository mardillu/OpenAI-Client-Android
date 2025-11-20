package com.mardillu.openai.network

import com.mardillu.openai.model.*
import com.mardillu.openai.model.requests.*
import com.mardillu.openai.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created on 26/03/2023 at 12:29 PM
 * @author mardillu
 */
interface ChatGptApiService {
        @Deprecated("Use Chat Completions instead")
        @POST("completions")
        suspend fun getTextCompletion(@Body request: TextCompletionRequest): TextCompletionResponse

        @POST("chat/completions")
        suspend fun getChatCompletion(@Body request: ChatCompletionRequest): ChatCompletionResponse

        @Deprecated("Use Chat Completions instead")
        @POST("edits")
        suspend fun getEditCompletion(@Body request: EditCompletionRequest): EditCompletionResponse

        @POST("embeddings")
        suspend fun getEmbeddings(@Body request: CreateEmbeddingRequest): CreateEmbeddingResponse

        @POST("images/generations")
        suspend fun createImage(@Body request: CreateImageRequest): CreateImageResponse

        @POST("moderations")
        suspend fun getModeration(@Body request: ModerationRequest): ModerationResponse

        @GET("models")
        suspend fun getModels(): GetModelsResponse

        @Multipart
        @POST("images/edits")
        suspend fun createImageEdit(
                @Part image: MultipartBody.Part,
                @Part mask: MultipartBody.Part,
                @Part("prompt") prompt: RequestBody,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): CreateImageResponse

        @Multipart
        @POST("images/edits")
        suspend fun createImageEdit(
                @Part image: MultipartBody.Part,
                @Part("prompt") prompt: RequestBody,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): CreateImageResponse

        @Multipart
        @POST("images/variations")
        suspend fun createImageVariation(
                @Part image: MultipartBody.Part,
                @Part("n") n: RequestBody,
                @Part("size") size: RequestBody
        ): CreateImageResponse

        @Multipart
        @POST("audio/transcriptions")
        suspend fun createTranscription(
                @Part file: MultipartBody.Part,
                @Part("model") model: RequestBody,
                @Part("prompt") prompt: RequestBody? = null,
                @Part("response_format") responseFormat: RequestBody? = null,
                @Part("temperature") temperature: RequestBody? = null,
                @Part("language") language: RequestBody? = null
        ): SimpleTextResponse

        @Multipart
        @POST("audio/translations")
        suspend fun createTranslation(
                @Part file: MultipartBody.Part,
                @Part("model") model: RequestBody,
                @Part("prompt") prompt: RequestBody? = null,
                @Part("response_format") responseFormat: RequestBody? = null,
                @Part("temperature") temperature: RequestBody? = null
        ): SimpleTextResponse

        // Files
        @GET("files")
        suspend fun listFiles(): ListFilesResponse

        @Multipart
        @POST("files")
        suspend fun uploadFile(
                @Part file: MultipartBody.Part,
                @Part("purpose") purpose: RequestBody
        ): FileObject

        @DELETE("files/{file_id}")
        suspend fun deleteFile(@Path("file_id") fileId: String): DeleteFileResponse

        @GET("files/{file_id}")
        suspend fun retrieveFile(@Path("file_id") fileId: String): FileObject

        @GET("files/{file_id}/content")
        suspend fun retrieveFileContent(@Path("file_id") fileId: String): ResponseBody

        // Fine-tuning
        @POST("fine_tuning/jobs")
        suspend fun createFineTuningJob(@Body request: CreateFineTuningJobRequest): FineTuningJob

        @GET("fine_tuning/jobs")
        suspend fun listFineTuningJobs(
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): ListFineTuningJobsResponse

        @GET("fine_tuning/jobs/{fine_tuning_job_id}")
        suspend fun retrieveFineTuningJob(@Path("fine_tuning_job_id") fineTuningJobId: String): FineTuningJob

        @POST("fine_tuning/jobs/{fine_tuning_job_id}/cancel")
        suspend fun cancelFineTuningJob(@Path("fine_tuning_job_id") fineTuningJobId: String): FineTuningJob

        @GET("fine_tuning/jobs/{fine_tuning_job_id}/events")
        suspend fun listFineTuningEvents(
                @Path("fine_tuning_job_id") fineTuningJobId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): ListFineTuningEventsResponse

        // Assistants
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("assistants")
        suspend fun createAssistant(@Body request: CreateAssistantRequest): Assistant

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("assistants")
        suspend fun listAssistants(
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): ListAssistantsResponse

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("assistants/{assistant_id}")
        suspend fun retrieveAssistant(@Path("assistant_id") assistantId: String): Assistant

        // Threads
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads")
        suspend fun createThread(@Body request: CreateThreadRequest): Thread

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}")
        suspend fun retrieveThread(@Path("thread_id") threadId: String): Thread

        @Headers("OpenAI-Beta: assistants=v2")
        @DELETE("threads/{thread_id}")
        suspend fun deleteThread(@Path("thread_id") threadId: String): DeleteFileResponse // Reusing DeleteFileResponse as structure is same

        // Messages
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/messages")
        suspend fun createMessage(
                @Path("thread_id") threadId: String,
                @Body request: CreateMessageRequest
        ): ThreadMessage

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/messages")
        suspend fun listMessages(
                @Path("thread_id") threadId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): ListMessagesResponse

        // Runs
        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs")
        suspend fun createRun(
                @Path("thread_id") threadId: String,
                @Body request: CreateRunRequest
        ): Run

        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs")
        @Streaming
        suspend fun createRunStream(
                @Path("thread_id") threadId: String,
                @Body request: CreateRunRequest
        ): ResponseBody

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/runs")
        suspend fun listRuns(
                @Path("thread_id") threadId: String,
                @Query("after") after: String? = null,
                @Query("limit") limit: Int? = 20
        ): ListRunsResponse

        @Headers("OpenAI-Beta: assistants=v2")
        @GET("threads/{thread_id}/runs/{run_id}")
        suspend fun retrieveRun(
                @Path("thread_id") threadId: String,
                @Path("run_id") runId: String
        ): Run

        @Headers("OpenAI-Beta: assistants=v2")
        @POST("threads/{thread_id}/runs/{run_id}/cancel")
        suspend fun cancelRun(
                @Path("thread_id") threadId: String,
                @Path("run_id") runId: String
        ): Run
}