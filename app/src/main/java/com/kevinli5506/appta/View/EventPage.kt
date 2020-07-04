package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinli5506.appta.EventAdapter
import com.kevinli5506.appta.EventData
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_event_list_page.*

class EventPage : AppCompatActivity() {
    var list: ArrayList<EventDonation> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list_page)

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        event_page_toolbar_tv.text = resources.getString(R.string.event)
        list.addAll(EventData.listEvent)
        event_rv_event.layoutManager = GridLayoutManager(this, 2)
        val eventAdapter = EventAdapter(list, 8)
        event_rv_event.adapter = eventAdapter
        eventAdapter.setOnItemClickCallBack(object :
            EventAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: EventDonation) {
                val intent = Intent(
                    this@EventPage,
                    EventDetailPage::class.java
                )
                intent.putExtra(EventDetailPage.EXTRA_EVENT, data)
                startActivity(intent)
            }
        })
    }
}
