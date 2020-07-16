package com.kevinli5506.appta.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


data class History(
    @SerializedName("id") val id:Int=0,
    @SerializedName("user_id") val userID: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("order_id") val orderID: Int? = null,
    @SerializedName("donation_id") val donationID: Int? = null,
    @SerializedName("withdrawal_id") val withdrawalID: Int? = null,
    @SerializedName("points") val points: Int = 0,
    @SerializedName("description") val detail: String = "",
    @SerializedName("updated_at") val dateString: String = "",
    @SerializedName("order") val orderId: OrderId? = null
)
data class OrderId(
    @SerializedName("orderproducts") val orderDetail:List<OrderDetail> = arrayListOf()
)
data class OrderDetail(
    @SerializedName("weight") val amount:Float = 0f,
    @SerializedName("category") val recycleItem : RecycleItem
)