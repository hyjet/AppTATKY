package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class History(
    @SerializedName("user_id") val userID: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("order_id") val orderID: Int? = null,
    @SerializedName("donation_id") val donationID: Int? = null,
    @SerializedName("withdrawal_id") val withdrawalID: Int? = null,
    @SerializedName("description") val detail: String = "",
    @SerializedName("updated_at") val dateString: String = "",
    var date: Calendar = Calendar.getInstance(),
    var historyType:String=""
) : Parcelable