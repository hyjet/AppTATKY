package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.SpecialPromo
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.SpecialPromoAdapter
import kotlinx.android.synthetic.main.activity_reward_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardPage : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_page)
        setSupportActionBar(reward_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        reward_toolbar_tv.text = resources.getString(R.string.reward)

        reward_rv_reward.layoutManager = GridLayoutManager(this, 2)
        refresh()

    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getVouchers().enqueue(object : Callback<CommonResponseModel<List<SpecialPromo>>> {
            override fun onFailure(
                call: Call<CommonResponseModel<List<SpecialPromo>>>?,
                t: Throwable?
            ) {
                Log.d("tes", "No Res")
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<SpecialPromo>>>,
                response: Response<CommonResponseModel<List<SpecialPromo>>>
            ) {
                Log.d("tes", "Res 200")
                val rewardResponse = response.body()
                if(rewardResponse.statusCode==200){
                    val list = rewardResponse.data
                    val specialPromoAdapter =
                        SpecialPromoAdapter(
                            list,
                            Int.MAX_VALUE
                        )
                    reward_rv_reward.adapter = specialPromoAdapter
                    specialPromoAdapter.setOnItemClickCallBack(object :
                        SpecialPromoAdapter.OnItemClickCallBack {
                        override fun onItemClicked(data: SpecialPromo) {
                            val intent = Intent(this@RewardPage, RewardDetailPage::class.java)
                            intent.putExtra(RewardDetailPage.EXTRA_REWARD, data)
                            startActivity(intent)
                        }
                    })
                }


            }

        })
    }
}