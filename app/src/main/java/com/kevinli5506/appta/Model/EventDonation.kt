package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDonation(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("images") var imageFile: String = "",
    @SerializedName("until_at") var untilDateString: String = "",
    @SerializedName("product_types") var productType : String =""
) : Parcelable