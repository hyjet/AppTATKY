package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_news_detail_page.*

class NewsDetailPage : AppCompatActivity() {
    private val list : ArrayList<Comment> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail_page)
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

}
