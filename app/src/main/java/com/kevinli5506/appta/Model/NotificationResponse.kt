package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("data") val notification: List<Notif> = arrayListOf()
)

data class Notif(
    @SerializedName("title") val title: String = "",
    @SerializedName("message") val message: String = ""
)