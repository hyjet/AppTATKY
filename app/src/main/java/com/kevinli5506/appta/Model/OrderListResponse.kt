package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class OrderListStatusResponse(
    @SerializedName("alreadyGenerated") val generatedOrder : List<OrderListResponse>,
    @SerializedName("haveNotGenerate") val notGeneratedOrder : List<OrderListResponse>
)


data class OrderListResponse(
    @SerializedName("address") val location:String =" ",
    @SerializedName("id") val id:Int=0,
    @SerializedName("picker_name") val picker_name:String="",
    @SerializedName("phone_number") val picker_phone:String="",
    @SerializedName("orderproducts") val orderproducts : List<OrderProducts>,
    var status:String


)

data class OrderProducts(
    @SerializedName("weight") val amount :Float =0f,
    @SerializedName("category")val recycleItem: RecycleItem,
    @SerializedName("updated_at")val date :String = ""
)
