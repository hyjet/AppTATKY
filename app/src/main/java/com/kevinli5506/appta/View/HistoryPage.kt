package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.HistoryAdapter
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.History
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_history_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HistoryPage : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_page)

        history_btn_back.setOnClickListener(this)
        history_rview_history.layoutManager = LinearLayoutManager(this)
        refresh()
        history_refresh_layout.setOnRefreshListener {
            refresh()
            history_refresh_layout.isRefreshing = false
        }
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getHistories().enqueue(object : Callback<CommonResponseModel<List<History>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<History>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(this@HistoryPage, t?.message, Toast.LENGTH_SHORT)
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
                        val list = historyResponse.data.reversed()
                        if (list.size > 0) {
                            history_tv_empty_message.visibility = View.GONE
                            val historyAdapter = HistoryAdapter(list)
                            history_rview_history.adapter = historyAdapter
                            historyAdapter.setOnItemClickCallBack(object :
                                HistoryAdapter.OnItemClickCallBack {
                                override fun onItemClicked(data: History) {
                                    val intent =
                                        Intent(this@HistoryPage, HistoryDetailPage::class.java)
                                    intent.putExtra(HistoryDetailPage.EXTRA_HISTORY, data.id)
                                    startActivity(intent)
                                }

                            })
                        } else {
                            history_tv_empty_message.text = getString(R.string.no_histories_message)
                        }

                    }
                } else {
                    try {
                        val jObjError =
                            JSONObject(response!!.errorBody()?.string())
                        val arrayError =
                            jObjError.getJSONObject("data").getJSONArray("error")

                        Toast.makeText(
                            this@HistoryPage,
                            arrayError.getString(0),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                                this@HistoryPage,
                                e.message,
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when(v){
            history_btn_back->{
                finish()
            }
        }
    }
}
