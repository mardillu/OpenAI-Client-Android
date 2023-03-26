package com.mardillu.openai

/**
 * Created on 26/03/2023 at 3:31 PM
 * @author mardillu
 */
class OpenAiInitializer {
    companion object {
        internal var CHAT_GPT_API_KEY = ""
        fun initialize(apiKey: String) {
            CHAT_GPT_API_KEY = apiKey
        }
    }
}