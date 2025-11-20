package com.mardillu.openai

import java.util.concurrent.TimeUnit

data class OpenAiConfig(
    val apiKey: String,
    val baseUrl: String = "https://api.openai.com/v1/",
    val timeout: Long = 60L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS
)
