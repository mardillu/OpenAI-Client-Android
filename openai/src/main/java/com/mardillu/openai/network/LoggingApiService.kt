package com.mardillu.openai.network

import com.mardillu.openai.model.requests.LogApiRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created on 06/04/2023 at 10:07 PM
 * @author mardillu
 */
interface LoggingApiService {
    @POST("log_request")
    fun logRequestTime(@Body request: LogApiRequest): Call<Any>
}