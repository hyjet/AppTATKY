package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDonation(
    @SerializedName("name") var name: String = "",
    @SerializedName("description")var description: String = "",
    @SerializedName("images")var imageFile: String = ""
):Parcelable