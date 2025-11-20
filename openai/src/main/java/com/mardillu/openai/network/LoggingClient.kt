package com.mardillu.openai.network

import com.mardillu.openai.BuildConfig

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
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created on 26/03/2023 at 12:55 PM
 * @author mardillu
 */
class LoggingClient {
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer XoqNd8F7x9PVZcrkcQCFYqW9M1x7CbsI")
                .header("Content-Type", "application/json")
                .build()

            chain.proceed(request)
        }

        writeTimeout(1, TimeUnit.MINUTES)
        readTimeout(1, TimeUnit.MINUTES)
        connectTimeout(1, TimeUnit.MINUTES)
    }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://linkshare.flatbuffer.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    private val apiService = retrofit.create(LoggingApiService::class.java)

    fun logRequestTime(
        route: String,
        request_time: Long,
        response_time: Long,
        duration: Long,
        responseCode: Int,
        method: String,
        accountName: String = "NA",
        userId: Long = 0L,
        exception: String = "",
        completionHandler: (Any?, Throwable?) -> Unit
    ) {
        val requestBody = LogApiRequest(
            route,
            request_time,
            response_time,
            duration,
            responseCode,
            method,
            accountName,
            userId,
            exception,
        )
        val call = apiService.logRequestTime(requestBody)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    completionHandler(result, null)
                } else {
                    val error = HttpException(response)
                    completionHandler(null, error)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                completionHandler(null, t)
            }
        })
    }

}


