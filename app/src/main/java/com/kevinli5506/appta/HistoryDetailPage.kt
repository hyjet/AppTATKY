package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_history_detail_page.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail_page)
        val history = intent.getParcelableExtra<History>(EXTRA_HISTORY)
        history_detail_tv_title.text = history.code
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val date:String = formatter.format(history.date.time).toString()
        history_detail_tv_date.text = date
        history_detail_tv_detail.text = history.detail
    }

    companion object {
        val EXTRA_HISTORY = "EXTRA_HISTORY"
    }
}
