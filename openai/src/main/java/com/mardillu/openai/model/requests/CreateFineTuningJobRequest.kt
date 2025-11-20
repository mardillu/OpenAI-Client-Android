package com.mardillu.openai.model.requests

data class CreateFineTuningJobRequest(
    val model: String,
    val training_file: String,
    val validation_file: String? = null,
    val hyperparameters: Hyperparameters? = null,
    val suffix: String? = null
)

data class Hyperparameters(
    val n_epochs: Any? = "auto" // "auto" or Int
)
