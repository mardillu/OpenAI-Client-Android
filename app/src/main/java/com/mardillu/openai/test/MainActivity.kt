package com.mardillu.openai.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mardillu.openai.model.Message
import com.mardillu.openai.network.OpenApiClient
import com.mardillu.openai.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatGptService = OpenApiClient()

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
    }
}