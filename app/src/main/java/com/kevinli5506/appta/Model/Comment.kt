package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("users_name") val name: String = "",
    @SerializedName("body") val content: String = ""
)