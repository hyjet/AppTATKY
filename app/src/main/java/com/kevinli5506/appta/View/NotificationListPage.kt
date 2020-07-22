package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.NotificationResponse
import com.kevinli5506.appta.NotificationAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_history_page.*
import kotlinx.android.synthetic.main.activity_notification_list_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationListPage : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list_page)

        notification_btn_back.setOnClickListener(this)
        notification_rview_notification.layoutManager = LinearLayoutManager(this)
        refresh()
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getAllNotification().enqueue(object : Callback<CommonResponseModel<List<NotificationResponse>>>{
            override fun onFailure(
                call: Call<CommonResponseModel<List<NotificationResponse>>>,
                t: Throwable
            ) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(this@NotificationListPage, t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<NotificationResponse>>>,
                response: Response<CommonResponseModel<List<NotificationResponse>>>
            ) {
                if (response?.code() == 200) {
                    Log.d("tes2", "Res 200")
                    val notificationResponse = response.body()
                    if (notificationResponse?.statusCode == 200) {
                        val list = notificationResponse.data
                        if (list.size > 0) {
                            notification_tv_empty_message.visibility = View.GONE
                            val notificationAdapter = NotificationAdapter(list)
                            notification_rview_notification.adapter = notificationAdapter
                            notificationAdapter.setOnItemClickCallBack(object :
                                NotificationAdapter.OnItemClickCallBack {
                                override fun onItemClicked(data: NotificationResponse) {
                                    val intent =
                                        Intent(this@NotificationListPage, NotificationDetailPage::class.java)
                                    intent.putExtra(NotificationDetailPage.EXTRA_NOTIFICATION_ID, data.id)
                                    startActivity(intent)
                                }

                            })
                        } else {
                            notification_tv_empty_message.text = getString(R.string.no_notification_message)
                        }

                    }
                } else {
                    try {
                        val jObjError =
                            JSONObject(response!!.errorBody()?.string())
                        val arrayError =
                            jObjError.getJSONObject("data").getJSONArray("error")

                        Toast.makeText(
                            this@NotificationListPage,
                            arrayError.getString(0),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@NotificationListPage,
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
            notification_btn_back->{
                finish()
            }
        }
    }
}