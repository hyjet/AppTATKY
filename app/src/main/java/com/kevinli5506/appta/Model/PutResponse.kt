package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class PutResponse (
    @SerializedName("message") val message:String ="",
    @SerializedName("error") var error: List<String>? = null
)