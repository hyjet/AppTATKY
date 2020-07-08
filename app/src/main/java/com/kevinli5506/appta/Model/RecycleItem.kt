package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class RecycleItem(
    @SerializedName("name") val itemType: String,
    @SerializedName("price_per_kg") val price: Double
)