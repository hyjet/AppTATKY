package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.History
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_history_detail_page.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailPage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail_page)
        val history = intent.getParcelableExtra<History>(EXTRA_HISTORY)
        history_detail_tv_title.text = history.title

        history_detail_tv_date.text = history.dateString
        history_detail_tv_detail.text = history.detail
    }

    companion object {
        val EXTRA_HISTORY = "EXTRA_HISTORY"
    }
}
