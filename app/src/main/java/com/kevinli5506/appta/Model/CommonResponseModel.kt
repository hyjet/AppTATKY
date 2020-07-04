package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class CommonResponseModel<T> (
    @SerializedName("status_code") var statusCode:Int,
    @SerializedName("data") var data : T
)