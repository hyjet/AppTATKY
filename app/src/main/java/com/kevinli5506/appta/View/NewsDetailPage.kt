package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.CommentAdapter
import com.kevinli5506.appta.Model.Comment
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.News
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.ApiService
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_news_detail_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailPage : BaseActivity(), View.OnClickListener {
    lateinit var news: News
    lateinit var apiClient: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail_page)
        news = intent.getParcelableExtra<News>(EXTRA_NEWS)

        news_detail_btn_back_navigation.setOnClickListener(this)
        news_detail_btn_rate.setOnClickListener(this)
        news_detail_btn_send.setOnClickListener(this)
        apiClient = ApiClient.getApiService(this)
        refresh()

        news_detail_edt_comment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (news_detail_edt_comment.text.toString().trim().equals("")) {
                    news_detail_btn_send.visibility = View.GONE
                } else
                    news_detail_btn_send.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }


    private fun refresh() {

        apiClient.getComments(news.id.toString())
            .enqueue(object : Callback<CommonResponseModel<List<Comment>>> {
                override fun onFailure(
                    call: Call<CommonResponseModel<List<Comment>>>?,
                    t: Throwable?
                ) {
                    Log.d("tes2", t?.message)
                }

                override fun onResponse(
                    call: Call<CommonResponseModel<List<Comment>>>?,
                    response: Response<CommonResponseModel<List<Comment>>>?
                ) {
                    if (response?.code() == 200) {
                        val commentResponse = response.body()
                        if (commentResponse.statusCode == 200) {
                            val list = commentResponse.data
                            news_detail_rv_comment.layoutManager =
                                LinearLayoutManager(this@NewsDetailPage)
                            val commentAdapter = CommentAdapter(list)
                            news_detail_rv_comment.adapter = commentAdapter
                        }
                    }
                }

            })
        apiClient.getSpecificNews(news.id.toString())
            .enqueue(object : Callback<CommonResponseModel<List<News>>> {
                override fun onFailure(
                    call: Call<CommonResponseModel<List<News>>>?,
                    t: Throwable?
                ) {
                    Log.d("tes2", t?.message)
                }

                override fun onResponse(
                    call: Call<CommonResponseModel<List<News>>>?,
                    response: Response<CommonResponseModel<List<News>>>?
                ) {
                    if (response?.code() == 200) {
                        val newsResponse = response.body()
                        if (newsResponse.statusCode == 200) {
                            val list = newsResponse.data
                            news = list[0]
                            val imageUrl =
                                "${Constants.BASE_STORAGE_URL}${Constants.STORAGE_NEWS_URL}${news.imageFile}"
                            Glide.with(this@NewsDetailPage)
                                .load(imageUrl)
                                .centerCrop()
                                .error(R.drawable.image_broken)
                                .fallback(R.drawable.image_null)
                                .placeholder(R.drawable.image_loading)
                                .into(news_detail_imgv_image)
                            news_detail_tv_title.setText(news.title)
                            news_detail_tv_rate.setText(news.rating.toString())
                            news_detail_tv_description.setText(news.description)
                            news_detail_tv_date.setText(news.time)
                        } else {
                            Log.d("tes2", "Code = ${response.code().toString()}")
                        }
                    }
                }

            })
    }

    companion object {
        val EXTRA_NEWS = "EXTRA_NEWS"
    }

    override fun onClick(v: View?) {
        when (v) {
            news_detail_btn_rate -> {
                val intent = Intent(this, NewsRatingActivity::class.java)
                intent.putExtra(EXTRA_NEWS, news)
                startActivity(intent)
            }
            news_detail_btn_back_navigation -> {
                finish()
            }
            news_detail_btn_send->{
                Log.d("tes2","tapped")
                val commentBody:String = news_detail_edt_comment.text.toString()
                apiClient.postComment(news.id,commentBody).enqueue(object : Callback<CommonResponseModel<PostResponse>>{
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
                val tmpIntent = intent
                finish()
                overridePendingTransition(0,0)
                startActivity(tmpIntent)
            }
        }
    }

}
