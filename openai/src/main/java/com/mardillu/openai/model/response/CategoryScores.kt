package com.mardillu.openai.model.response

import com.google.gson.annotations.SerializedName

data class CategoryScores(
    val hate: Double,
    @SerializedName("hate/threatening")
    val hateThreatening: Double,
    @SerializedName("self-harm")
    val selfHarm: Double,
    val sexual: Double,
    @SerializedName("sexual/minors")
    val sexualMinors: Double,
    val violence: Double,
    @SerializedName("violence/graphic")
    val violenceGraphic: Double
)