package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class News(
    @SerializedName("id") var id:Int =0,
    @SerializedName("title") var title: String = "",
    @SerializedName("rate")var rating: Float = 0f,
    @SerializedName("updated_at")var time: String = "",
    @SerializedName("images")var imageFile: String = "",
    @SerializedName("body")var description: String = ""
) : Parcelable