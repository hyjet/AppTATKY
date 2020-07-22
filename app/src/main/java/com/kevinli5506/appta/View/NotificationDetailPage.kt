package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.NotificationResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_history_page.*
import kotlinx.android.synthetic.main.activity_notification_detail_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NotificationDetailPage : BaseActivity(),View.OnClickListener {
    var notificationId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_detail_page)

        notification_detail_btn_back.setOnClickListener(this)
        notificationId = intent.getStringExtra(EXTRA_NOTIFICATION_ID)
        refresh()


    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getNotificationDetail(notificationId)
            .enqueue(object : Callback<List<NotificationResponse>> {
                override fun onFailure(call: Call<List<NotificationResponse>>, t: Throwable) {
                    Log.d("tes2", t?.message)
                    val toast =
                        Toast.makeText(this@NotificationDetailPage, t?.message, Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<List<NotificationResponse>>,
                    response: Response<List<NotificationResponse>>
                ) {
                    if (response?.code() == 200) {
                        Log.d("tes2", "Res 200")
                        val notificationDetail = response.body()
                        if (notificationDetail != null) {
                            notification_detail_tv_title.text =
                                notificationDetail[0].notification[0].title
                            notification_detail_tv_message.text =
                                notificationDetail[0].notification[0].message
                            val StDformatter = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH)
                            val date: Date =StDformatter.parse(notificationDetail[0].dateString)
                            val DtSformatter = SimpleDateFormat("dd MMMM YYYY HH:mm:ss", Locale.ENGLISH)
                            val dateString = DtSformatter.format(date)
                            notification_detail_tv_date.text = dateString
                        }

                    } else {
                        try {
                            val jObjError =
                                JSONObject(response!!.errorBody()?.string())
                            val arrayError =
                                jObjError.getJSONObject("data").getJSONArray("error")

                            Toast.makeText(
                                this@NotificationDetailPage,
                                arrayError.getString(0),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@NotificationDetailPage,
                                e.message,
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }

                }
            })
    }

    companion object {
        val EXTRA_NOTIFICATION_ID = "EXTRANOTIFICATIONID"
    }

    override fun onClick(v: View?) {
        when(v){
            notification_detail_btn_back->{
                finish()
            }
        }
    }
}