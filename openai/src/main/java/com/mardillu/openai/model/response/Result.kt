package com.mardillu.openai.model.response

data class Result(
    var categories: Categories,
    var category_scores: CategoryScores,
    var flagged: Boolean
)