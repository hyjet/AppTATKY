package com.kevinli5506.appta.Model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    var authToken: String? = null,
    @SerializedName("error")
    var errorMessage: String? = null


)