package com.mardillu.openai.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mardillu.openai.OpenAiConfig
import com.mardillu.openai.model.Message
import com.mardillu.openai.network.OpenApiClient
import com.mardillu.openai.test.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize config (Replace with actual key or load from secure storage)
        val config = OpenAiConfig(apiKey = "YOUR_API_KEY_HERE")
        val chatGptService = OpenApiClient(config)

        lifecycleScope.launch {
            try {
                val result = chatGptService.getTextCompletion("Hello chat gpt! what is the meaning of life?")
                binding.result1.text = result.choices[0].text
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in getTextCompletion", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.getChatCompletion(messages = listOf(Message("user", "What is the update with your weekly PR review")))
                binding.result2.text = result.choices[0].message.content
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in getChatCompletion", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.getEditCompletion(input = "What day of the wek is it?", instruction = "Fix the spelling mistakes")
                binding.result3.text = result.choices[0].text
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in getEditCompletion", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.getEmbeddings("Hello chat gpt! what is the meaning of life?")
                binding.result4.text = result.data[0].embedding.size.toString()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in getEmbeddings", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.createImage("A cute baby sea otter")
                binding.result5.text = result.data[0].url
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in createImage", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.getModeration("I want to kill them.")
                binding.result6.text = result.results[0].categories.hate.toString()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in getModeration", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.createImageEdit(
                    imageFromAssets("img.png"),
                    "A cute cat sitting on a white table",
                    imageFromAssets("img.png")
                )
                binding.result7.text = result.data[0].url
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in createImageEdit", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.createImageVariation(imageFromAssets("img.png"))
                binding.result8.text = result.data[0].url
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in createImageVariation", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.createTranscription(imageFromAssets("audio.m4a"))
                binding.result9.text = result.text
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in createTranscription", e)
            }
        }

        lifecycleScope.launch {
            try {
                val result = chatGptService.createTranslation(imageFromAssets("audio.m4a"))
                binding.result10.text = result.text
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in createTranslation", e)
            }
        }
    }

    private fun imageFromAssets(name: String): File {
        val inputStream = applicationContext.assets.open(name)
        val suf = if (name.contains("m4a")) "suf.m4a" else "suf"
        val file = File.createTempFile("pre", suf)
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        inputStream.close()

        return file
    }
}