package com.kevinli5506.appta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {
    private val list: ArrayList<News> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(NewsData.listNews)
        news_rv_news.layoutManager = LinearLayoutManager(view.context)
        val newsAdapter = NewsNewsAdapter(list)
        news_rv_news.adapter = newsAdapter
        newsAdapter.setOnItemClickCallBack(object :NewsNewsAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: News) {
                val intent = Intent(view.context,NewsDetailPage::class.java)
                intent.putExtra(NewsDetailPage.EXTRA_NEWS,data)
                startActivity(intent)
            }
        })
    }

}
