package com.mardillu.openai.model.response

import com.google.gson.annotations.SerializedName

data class Categories(
    val hate: Boolean,
    @SerializedName("hate/threatening")
    val hateThreatening: Boolean,
    @SerializedName("self-harm")
    val selfHarm: Boolean,
    val sexual: Boolean,
    @SerializedName("sexual/minors")
    val sexualMinors: Boolean,
    val violence: Boolean,
    @SerializedName("violence/graphic")
    val violenceGraphic: Boolean
)