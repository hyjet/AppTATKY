package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_news_rating.*

class NewsRatingActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_rating)

        news_rating_btn_rate.setOnClickListener(this)
        news_rating_btn_back_navigation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            news_rating_btn_back_navigation->{
                finish()
            }
            news_rating_btn_rate->{
                val rate = news_rating_rate_bar.numStars
                finish()
            }
        }
    }
}
