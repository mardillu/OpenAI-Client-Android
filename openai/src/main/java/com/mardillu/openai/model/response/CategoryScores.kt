package com.mardillu.openai.model.response

import com.google.gson.annotations.SerializedName

data class CategoryScores(
    var hate: Double,
    @SerializedName("hate/threatening")
    var hateThreatening: Double,
    @SerializedName("self-harm")
    var selfHarm: Double,
    var sexual: Double,
    @SerializedName("sexual/minors")
    var sexualMinors: Double,
    var violence: Double,
    @SerializedName("violence/graphic")
    var violenceGraphic: Double
)