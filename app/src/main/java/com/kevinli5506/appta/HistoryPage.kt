package com.kevinli5506.appta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history_page.*

class HistoryPage : AppCompatActivity() {
    private val list:ArrayList<History> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_page)
        list.addAll(HistoryData.listHistory)
        Log.d("test",list.size.toString())
        history_rview_history.layoutManager = LinearLayoutManager(this)
        val historyAdapter = HistoryAdapter(list)
        history_rview_history.adapter = historyAdapter
        historyAdapter.setOnItemClickCallBack(object :HistoryAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: History) {
                val intent = Intent(this@HistoryPage, HistoryDetailPage::class.java)
                intent.putExtra(HistoryDetailPage.EXTRA_HISTORY, data)
                startActivity(intent)
            }

        })
    }
}
