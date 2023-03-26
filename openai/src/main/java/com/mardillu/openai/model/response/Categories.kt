package com.mardillu.openai.model.response

import com.google.gson.annotations.SerializedName

data class Categories(
    var hate: Boolean,
    @SerializedName("hate/threatening")
    var hateThreatening: Boolean,
    @SerializedName("self-harm")
    var selfHarm: Boolean,
    var sexual: Boolean,
    @SerializedName("sexual/minors")
    var sexualMinors: Boolean,
    var violence: Boolean,
    @SerializedName("violence/graphic")
    var violenceGraphic: Boolean
)