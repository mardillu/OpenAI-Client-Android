package com.mardillu.openai.model.response

import com.mardillu.openai.model.requests.Hyperparameters

data class FineTuningJob(
    val id: String,
    val `object`: String,
    val created_at: Long,
    val finished_at: Long? = null,
    val model: String,
    val fine_tuned_model: String? = null,
    val organization_id: String,
    val result_files: List<String>,
    val status: String,
    val validation_file: String? = null,
    val training_file: String,
    val hyperparameters: Hyperparameters,
    val trained_tokens: Int? = null
)
