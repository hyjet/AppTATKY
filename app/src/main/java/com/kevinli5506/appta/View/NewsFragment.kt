package com.kevinli5506.appta.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.News
import com.kevinli5506.appta.NewsNewsAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_rv_news.layoutManager = LinearLayoutManager(view.context)

        refresh()
        news_page_refresh_layout.setOnRefreshListener {
            refresh()
            news_page_refresh_layout.isRefreshing = false
        }

    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(context!!) //Todo : Check context
        apiClient.getNews().enqueue(object : Callback<CommonResponseModel<List<News>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<News>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<News>>>?,
                response: Response<CommonResponseModel<List<News>>>?
            ) {
                if (response?.code() == 200) {
                    val newsResponse = response.body()
                    if (newsResponse?.statusCode == 200) {
                        val list = newsResponse.data
                        val newsAdapter = NewsNewsAdapter(list)
                        news_rv_news.adapter = newsAdapter
                        newsAdapter.setOnItemClickCallBack(object :
                            NewsNewsAdapter.OnItemClickCallBack {
                            override fun onItemClicked(data: News) {
                                val intent = Intent(
                                    context,
                                    NewsDetailPage::class.java
                                )
                                intent.putExtra(NewsDetailPage.EXTRA_NEWS, data)
                                startActivity(intent)
                            }
                        })
                    } else {
                        Log.d("tes2", "Code = ${response.code().toString()}")
                    }
                }
            }

        })
    }

}
