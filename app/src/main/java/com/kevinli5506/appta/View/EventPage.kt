package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.EventAdapter
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_event_list_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventPage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list_page)

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        event_page_toolbar_tv.text = resources.getString(R.string.event)
        event_rv_event.layoutManager = GridLayoutManager(this, 2)
        refresh()
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getEvents().enqueue(object : Callback<CommonResponseModel<List<EventDonation>>> {
            override fun onFailure(
                call: Call<CommonResponseModel<List<EventDonation>>>?,
                t: Throwable?
            ) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(this@EventPage,t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<EventDonation>>>?,
                response: Response<CommonResponseModel<List<EventDonation>>>?
            ) {
                if (response?.code() == 200) {
                    val eventResponse = response.body()
                    if (eventResponse.statusCode == 200) {
                        val list = eventResponse.data
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
                } else {
                    Log.d("test2", "Code = ${response?.code().toString()}")
                }

            }

        })
    }
}
