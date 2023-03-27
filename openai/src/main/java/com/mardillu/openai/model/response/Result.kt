package com.mardillu.openai.model.response

data class Result(
    val categories: Categories,
    val category_scores: CategoryScores,
    val flagged: Boolean
)