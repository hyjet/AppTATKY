package com.kevinli5506.appta.Rest

import android.graphics.drawable.Drawable
import com.kevinli5506.appta.Model.*
import com.kevinli5506.appta.OrderAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<CommonResponseModel<LoginResponse>>

    @POST(Constants.POST_RATING_URL)
    @FormUrlEncoded
    fun postRate(
        @Field("article_id") article_id: Int,
        @Field("rate") rate: Int
    ): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_COMMENT_URL)
    @FormUrlEncoded
    fun postComment(
        @Field("article_id") article_id: Int,
        @Field("body") body: String
    ): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_DONATION_URL)
    @FormUrlEncoded
    fun postDonation(
        @Field("event_id") event_id: Int,
        @Field("amounts") amount: Int,
        @Field("images") image: Drawable//Todo:Correct type
    ): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_ORDER_URL)
    fun postOrder(
        @Body body: OrderRequest
    ): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_WITHDRAW_URL)
    @FormUrlEncoded
    fun postWithdraw(
        @Field("full_name") fullName: String,
        @Field("account_number") account: String,
        @Field("bank_name") bankName: String,
        @Field("nominal") amount: Int
    ): Call<CommonResponseModel<PostResponse>>

    @PUT(Constants.PUT_EDIT_PROFILE_URL)
    @FormUrlEncoded
    fun putEditProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone_number") phone: String
    ): Call<CommonResponseModel<PutResponse>>

    @GET(Constants.VOUCHER_URL)
    fun getVouchers(): Call<CommonResponseModel<List<SpecialPromo>>>

    @GET(Constants.EVENT_URL)
    fun getEvents(): Call<CommonResponseModel<List<EventDonation>>>

    @GET(Constants.HISTORY_URL)
    fun getHistories(): Call<CommonResponseModel<List<History>>>

    @GET(Constants.RECYCLE_ITEM_URL)
    fun getRecycleItemCategory(): Call<CommonResponseModel<List<RecycleItem>>>

    @GET(Constants.NEWS_URL)
    fun getNews(): Call<CommonResponseModel<List<News>>>

    @GET("${Constants.NEWS_URL}/{articleID}")
    fun getSpecificNews(@Path("articleID") path: String): Call<CommonResponseModel<List<News>>>

    @GET("${Constants.COMMENT_URL}/{articleID}")
    fun getComments(@Path("articleID") path: String): Call<CommonResponseModel<List<Comment>>>

    @GET(Constants.USER_URL)
    fun getUser(): Call<CommonResponseModel<List<User>>>
}