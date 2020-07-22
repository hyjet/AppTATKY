package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.HistoryDetailAdapter
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.History
import com.kevinli5506.appta.Model.OrderDetail
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_event_list_page.*
import kotlinx.android.synthetic.main.activity_history_detail_page.*
import kotlinx.android.synthetic.main.activity_history_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailPage : BaseActivity(),View.OnClickListener {
    var historyId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail_page)
        history_detail_btn_back.setOnClickListener(this)

        historyId = intent.getIntExtra(EXTRA_HISTORY, 0)
        history_detail_detail_layout.visibility = View.GONE
        refresh()

    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getHistoryDetail(historyId.toString())
            .enqueue(object : Callback<CommonResponseModel<List<History>>> {
                override fun onFailure(call: Call<CommonResponseModel<List<History>>>?, t: Throwable?) {
                    Log.d("tes2", t?.message)
                    val toast =
                        Toast.makeText(this@HistoryDetailPage, t?.message, Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<CommonResponseModel<List<History>>>?,
                    response: Response<CommonResponseModel<List<History>>>?
                ) {
                    if (response?.code() == 200) {
                        Log.d("tes2", "Res 200")
                        val historyResponse = response.body()
                        if (historyResponse?.statusCode == 200) {
                            val history = historyResponse.data[0]
                            history_detail_tv_title.text = history.title
                            history_detail_tv_date.text = history.dateString
                            history_detail_tv_detail.text = history.detail
                            val orderId = history.orderId
                            if (orderId != null) {
                            val listOrder: List<OrderDetail> = orderId.orderDetail
                                history_detail_rview_history.layoutManager =
                                    LinearLayoutManager(this@HistoryDetailPage)
                                val adapter = HistoryDetailAdapter(listOrder)
                                history_detail_rview_history.adapter = adapter
                                history_detail_detail_layout.visibility = View.VISIBLE
                            } else {
                                history_detail_detail_layout.visibility = View.GONE
                            }
                        }
                    } else {
                        Log.d("test2", "Code = ${response?.code().toString()}")
                    }
                }

            })
    }

    companion object {
        val EXTRA_HISTORY = "EXTRA_HISTORY"
    }

    override fun onClick(v: View?) {
        when(v){
            history_detail_btn_back->{
                finish()
            }
        }
    }
}
