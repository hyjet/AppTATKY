package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName
import com.kevinli5506.appta.OrderAdapter

class OrderRequest (
    @SerializedName("coordinate_x") val longitude:Double,
    @SerializedName("coordinate_y")val latitude:Double,
    @SerializedName("address")val address:String,
    @SerializedName("address_description")val address_desc:String,
    @SerializedName("phone_number")val phone:String,
    @SerializedName("products")val products:List<OrderAdapter.OrderData>
)