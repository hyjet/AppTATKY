package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.News
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_news_rating.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRatingActivity : BaseActivity(), View.OnClickListener {
    lateinit var news: News
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_rating)
        news = intent.getParcelableExtra(NewsDetailPage.EXTRA_NEWS)
        val imageUrl = "${Constants.BASE_STORAGE_URL}${Constants.STORAGE_NEWS_URL}${news.imageFile}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(news_rating_imgv_image)
        news_rating_tv_title.setText(news.title)
        news_rating_tv_rate.setText(news.rating.toString())
        news_rating_tv_date.setText(news.time)

        news_rating_btn_rate.setOnClickListener(this)
        news_rating_btn_back_navigation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            news_rating_btn_back_navigation -> {
                finish()
            }
            news_rating_btn_rate -> {
                val rate = news_rating_rate_bar.numStars
                val apiInterface = ApiClient.getApiService(this)
                apiInterface.postRate(news.id,rate).enqueue(object : Callback<CommonResponseModel<PostResponse>>{
                    override fun onFailure(
                        call: Call<CommonResponseModel<PostResponse>>?,
                        t: Throwable?
                    ) {
                        Log.d("tes2",t?.message)
                    }

                    override fun onResponse(
                        call: Call<CommonResponseModel<PostResponse>>?,
                        response: Response<CommonResponseModel<PostResponse>>?
                    ) {
                        if(response?.code()==200){
                            val postResponse = response.body()
                            if (postResponse.statusCode==200){
                                val message = postResponse.data.message
                                Log.d("tes2",message)
                            }
                            else{
                                val errorMessage = postResponse.data.error?.get(0)
                                Log.d("tes2",errorMessage)
                            }
                        }
                        else {
                            Log.d("tes2", "Code = ${response?.code().toString()}")
                        }
                    }

                })
                finish()
            }
        }
    }
}
