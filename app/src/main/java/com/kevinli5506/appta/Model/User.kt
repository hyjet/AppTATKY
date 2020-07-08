package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("phone_number") val phone: String = "",
    @SerializedName("points") val points :Int = 0
)