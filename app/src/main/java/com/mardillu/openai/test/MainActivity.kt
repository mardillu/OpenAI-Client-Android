package com.mardillu.openai.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mardillu.openai.model.Message
import com.mardillu.openai.network.LoggingApiService
import com.mardillu.openai.network.LoggingClient
import com.mardillu.openai.network.OpenApiClient
import com.mardillu.openai.test.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatGptService = OpenApiClient()
        val loggingApiService = LoggingClient()

//        loggingApiService.logRequestTime(
//            "test/test",
//            200,
//            500,
//            300,
//            200,
//        ){ result, error ->
//            if (error != null){
//                Log.d("TAG", "onCreate: =======> FAILED <============")
//                error.printStackTrace()
//            } else {
//                Log.d("TAG", "onCreate: =======> SUCCESS <============")
//            }
//        }

        chatGptService.getTextCompletion("Hello chat gpt! what is the meaning of life?") { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result1.text = result.choices[0].text
            }
        }

        chatGptService.getChatCompletion(messages = listOf(Message("user", "What is the update with your weekly PR review"))) { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result2.text = result.choices[0].message.content
            }
        }

        chatGptService.getEditCompletionAlt(input = "What day of the wek is it?", instruction = "Fix the spelling mistakes") { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result3.text = result.choices[0].text
            }
        }

        chatGptService.getEmbeddings("Hello chat gpt! what is the meaning of life?") { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result4.text = result.data[0].embedding.size.toString()
            }
        }

        chatGptService.createImage("A cute baby sea otter") { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result5.text = result.data[0].url
            }
        }

        chatGptService.getModeration("I want to kill them.") { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result6.text = result.results[0].categories.hate.toString()
            }
        }

        chatGptService.createImageEdit(imageFromAssets("img.png"), "A cute cat sitting on a white table", imageFromAssets("img.png")) { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result7.text = result.data[0].url
            }
        }

        chatGptService.createImageVariation(imageFromAssets("img.png")) { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result8.text = result.data[0].url
            }
        }

        chatGptService.createTranscription(imageFromAssets("audio.m4a")) { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result9.text = result.text
            }
        }

        chatGptService.createTranslation(imageFromAssets("audio.m4a")) { result, error ->
            if (error != null) {
                // Handle error
            } else if (result != null) {
                binding.result10.text = result.text
            }
        }


    }

    private fun imageFromAssets(name: String): File {
        val inputStream = applicationContext.assets.open(name)
        val suf = if (name.contains("m4a")) "suf.m4a" else "suf"
        val file = File.createTempFile("pre",suf)
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        inputStream.close()

        return file
    }
}