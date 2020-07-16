package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Model.SpecialPromo
import com.kevinli5506.appta.Model.SpecialPromoRedeemStatus
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_reward_detail_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class RewardDetailPage : BaseActivity(), View.OnClickListener {
    var claimed: Boolean = false
    val apiClient = ApiClient.getApiService(this)
    lateinit var specialPromo: SpecialPromo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_detail_page)
        specialPromo = intent.getParcelableExtra(EXTRA_REWARD)
        refresh()
        val imageUrl =
            "${Constants.BASE_STORAGE_URL}${Constants.STORAGE_VOUCHER_URL}${specialPromo.imageFile}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(reward_detail_imgv_reward_image)
        reward_detail_tv_description_detail.text = createIndentedText(specialPromo.detail,48,0)
        reward_detail_tv_outlet_discount.text = specialPromo.name
        reward_detail_tv_voucher_code_content.text = specialPromo.code
        val df = DecimalFormat("#,###")
        val pricetext ="Rp. "+df.format(specialPromo.price)
        reward_detail_tv_voucher_price_content.text = pricetext
        reward_detail_btn_claim.setOnClickListener(this)
        reward_detail_btn_back_navigation.setOnClickListener(this)

    }

    private fun refresh() {
        apiClient.getVoucherRedeemStatus(specialPromo.id.toString())
            .enqueue(object : Callback<CommonResponseModel<SpecialPromoRedeemStatus>> {
                override fun onFailure(
                    call: Call<CommonResponseModel<SpecialPromoRedeemStatus>>?,
                    t: Throwable?
                ) {
                    Log.d("tes2", t?.message)
                    val toast = Toast.makeText(this@RewardDetailPage,t?.message, Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<CommonResponseModel<SpecialPromoRedeemStatus>>?,
                    response: Response<CommonResponseModel<SpecialPromoRedeemStatus>>?
                ) {
                    if (response?.code() == 200) {
                        val specialPromoRedeemStatusResponse = response.body()
                        if (specialPromoRedeemStatusResponse?.statusCode == 200) {
                            val redeem = specialPromoRedeemStatusResponse.data.status
                            claimed = redeem != 0
                            if (claimed) {
                                reward_detail_btn_claim.visibility = View.GONE
                                reward_detail_tv_voucher_code_title.visibility = View.VISIBLE
                                reward_detail_tv_voucher_code_content.visibility = View.VISIBLE
                                reward_detail_tv_voucher_price_title.visibility = View.GONE
                                reward_detail_tv_voucher_price_content.visibility = View.GONE
                            }
                        } else {
                            val errorMessage = specialPromoRedeemStatusResponse?.message
                            Log.d("tes2", errorMessage)
                        }
                    } else {
                        Log.d(
                            "tes2",
                            "Code = ${response?.code().toString()}, msg = ${response?.message()}"
                        )
                    }
                }

            })
    }

    override fun onClick(v: View?) {
        when (v) {
            reward_detail_btn_back_navigation -> {
                finish()
            }
            reward_detail_btn_claim -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Klaim")
                builder.setMessage("Apakah anda yakin akan mengklaim hadiah ini?")
                builder.setPositiveButton("Yes") { _, _ ->
                    apiClient.postRedeemVoucher(specialPromo.id)
                        .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                            override fun onFailure(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                t: Throwable?
                            ) {
                                Log.d("tes2", t?.message)
                                val toast = Toast.makeText(this@RewardDetailPage,t?.message, Toast.LENGTH_SHORT)
                                toast.show()
                            }

                            override fun onResponse(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                response: Response<CommonResponseModel<PostResponse>>?
                            ) {
                                if (response?.code() == 200) {
                                    val postResponse = response.body()
                                    if (postResponse?.statusCode == 200) {
                                        val message = postResponse.data.message
                                        Log.d("tes2", message)
                                        reward_detail_btn_claim.visibility = View.GONE
                                        reward_detail_tv_voucher_code_title.visibility =
                                            View.VISIBLE
                                        reward_detail_tv_voucher_code_content.visibility =
                                            View.VISIBLE
                                        reward_detail_tv_voucher_price_title.visibility = View.GONE
                                        reward_detail_tv_voucher_price_content.visibility =
                                            View.GONE
                                    } else {
                                        val errorMessage = postResponse?.data?.error?.get(0)
                                        Log.d("tes2", errorMessage)
                                    }
                                } else {
                                    Log.d(
                                        "tes2",
                                        "Code = ${response?.code()
                                            .toString()}, msg = ${response?.message()}"
                                    )
                                }
                            }

                        })
                    claimed = true
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.show()
            }
        }
    }

    companion object {
        var EXTRA_REWARD = "EXTRA_REWARD"
    }
}
