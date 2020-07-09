package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecialPromo(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("description") var detail: String = "",
    @SerializedName("images") var imageFile: String = "",
    @SerializedName("code") var code: String = "",
    @SerializedName("voucher_price") var price: Int = 0
) : Parcelable