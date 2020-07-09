package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("error") var error: List<String>? = null
)