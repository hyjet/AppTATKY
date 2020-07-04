package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kevinli5506.appta.CommentAdapter
import com.kevinli5506.appta.CommentsData
import com.kevinli5506.appta.Model.Comment
import com.kevinli5506.appta.Model.News
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_news_detail_page.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsDetailPage : AppCompatActivity() ,View.OnClickListener{
    private val list : ArrayList<Comment> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail_page)
        val news = intent.getParcelableExtra<News>(EXTRA_NEWS)
        Glide.with(this)
            .load(news.image)
            .into(news_detail_imgv_image)
        news_detail_tv_title.setText(news.title)
        news_detail_tv_rate.setText(news.rating.toString())
        news_detail_tv_description.setText(news.description)
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val date:String = formatter.format(news.time.time).toString()
        news_detail_tv_date.setText(date)

        news_detail_btn_back_navigation.setOnClickListener(this)
        news_detail_tv_rate.setOnClickListener(this)

        list.addAll(CommentsData.listComments)
        news_detail_rv_comment.layoutManager = LinearLayoutManager(this)
        val commentAdapter = CommentAdapter(list)
        news_detail_rv_comment.adapter= commentAdapter
        news_detail_edt_comment.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(news_detail_edt_comment.text.toString().trim().equals("")){
                    news_detail_btn_send.visibility = View.GONE
                }
                else
                    news_detail_btn_send.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
    companion object{
        val EXTRA_NEWS ="EXTRA_NEWS"
    }

    override fun onClick(v: View?) {
        when(v){
            news_detail_tv_rate->{

            }
            news_detail_btn_back_navigation->{
                finish()
            }
        }
    }

}
