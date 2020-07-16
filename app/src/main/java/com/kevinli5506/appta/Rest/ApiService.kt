package com.kevinli5506.appta.Rest


import com.kevinli5506.appta.Model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @POST(Constants.POST_REDEEM_VOUCHER)
    @FormUrlEncoded
    fun postRedeemVoucher(
        @Field("voucher_id") id: Int
    ): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_COMMENT_URL)
    @FormUrlEncoded
    fun postComment(
        @Field("article_id") article_id: Int,
        @Field("body") body: String
    ): Call<CommonResponseModel<PostResponse>>

    @Multipart
    @POST(Constants.POST_DONATION_URL)
    fun postDonation(
        @Part("event_id") event_id: Int,
        @Part("amounts") amount: Int,
        @Part image: MultipartBody.Part
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

    @Multipart
    @POST(Constants.POST_SIGN_UP_URL)

    fun postSignup(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        //@Part("identity_number") identity_number:Int,
        @Part identity_card: MultipartBody.Part
    ): Call<CommonResponseModel<LoginResponse>>

    @POST(Constants.POST_SIGN_OUT_URL)
    fun postSignOut(): Call<CommonResponseModel<PostResponse>>

    @POST(Constants.POST_FORGET_PASSWORD)
    @FormUrlEncoded
    fun postForgetPassword(
        @Field("email") email: String
    ):Call<CommonResponseModel<PostResponse>>


    @PUT(Constants.PUT_EDIT_PROFILE_URL)
    @FormUrlEncoded
    fun putEditProfile(
        @FieldMap editProfileRequest: Map<String, String>
    ): Call<CommonResponseModel<PutResponse>>

    @GET(Constants.VOUCHER_URL)
    fun getVouchers(): Call<CommonResponseModel<List<SpecialPromo>>>

    @GET(Constants.EVENT_URL)
    fun getEvents(): Call<CommonResponseModel<List<EventDonation>>>

    @GET("${Constants.VOUCHER_URL}/{voucherID}")
    fun getVoucherRedeemStatus(@Path("voucherID") path: String): Call<CommonResponseModel<SpecialPromoRedeemStatus>>

    @GET(Constants.HISTORY_URL)
    fun getHistories(): Call<CommonResponseModel<List<History>>>

    @GET("${Constants.HISTORY_URL}/{historyID}")
    fun getHistoryDetail(@Path("historyID") path: String): Call<CommonResponseModel<List<History>>>

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

    @GET(Constants.ORDER_URL)
    fun getOrderList(): Call<CommonResponseModel<OrderListStatusResponse>>

    @GET("${Constants.POST_ORDER_URL}/{orderId}")
    fun getCancelOrder(@Path("orderId") path:String):Call<CommonResponseModel<PostResponse>>

    @GET(Constants.NOTIF_URL)
    fun getnotification(): Observable<CommonResponseModel<List<NotificationResponse>>>
}