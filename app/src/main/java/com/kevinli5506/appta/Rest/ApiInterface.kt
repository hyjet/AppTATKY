package com.kevinli5506.appta.Rest

import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.LoginRequest
import com.kevinli5506.appta.Model.LoginResponse
import com.kevinli5506.appta.Model.SpecialPromo
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password:String
    ): Call<CommonResponseModel<LoginResponse>>

    @GET(Constants.VOUCHER_URL)
    fun getVouchers(): Call<CommonResponseModel<List<SpecialPromo>>>
}