package com.mardillu.openai.test

import android.app.Application
import com.mardillu.openai.OpenAiInitializer

/**
 * Created on 26/03/2023 at 5:49 PM
 *
 * @author mardillu
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        OpenAiInitializer.initialize(getString(R.string.OPEN_AI_API_KEY))
    }
}