package com.kevinli5506.appta.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinli5506.appta.EventAdapter
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.home_imgbtn_withdrawal
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class HomeFragment : Fragment(), View.OnClickListener {
    private val list: ArrayList<EventDonation> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.home_rv_event.layoutManager = GridLayoutManager(view.context, 2)

        view.home_imgbtn_withdrawal.setOnClickListener(this)
        view.home_imgbtn_history.setOnClickListener(this)
        view.home_imgbtn_reward.setOnClickListener(this)
        view.home_imgbtn_donation.setOnClickListener(this)
        view.home_imgbtn_calculate.setOnClickListener(this)
        view.home_imgbtn_current_order.setOnClickListener(this)
        refresh()
        view.home_refresh_layout.setOnRefreshListener {
            refresh()
            view.home_refresh_layout.isRefreshing = false
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            home_imgbtn_withdrawal -> {
                val intent = Intent(
                    context,
                    WithdrawPage::class.java
                )
                startActivity(intent)
            }
            home_imgbtn_history -> {
                val intent = Intent(
                    context,
                    HistoryPage::class.java
                )
                startActivity(intent)
            }
            home_imgbtn_reward -> {
                val intent = Intent(
                    context,
                    RewardPage::class.java
                )
                startActivity(intent)
            }
            home_imgbtn_donation -> {
                val intent = Intent(
                    context,
                    EventPage::class.java
                )
                startActivity(intent)
            }
            home_imgbtn_calculate -> {
                val intent = Intent(
                    context,
                    CalculatePage::class.java
                )
                startActivity(intent)
            }
            home_imgbtn_current_order->{
                val intent = Intent(
                    context,
                    OrderListPage::class.java
                )
                startActivity(intent)
            }
        }
    }

    private fun refresh() {
        Log.d("tes2", "Hello")
        val apiClient = ApiClient.getApiService(context!!) //Todo : Check context
        apiClient.getEvents().enqueue(object : Callback<CommonResponseModel<List<EventDonation>>> {
            override fun onFailure(
                call: Call<CommonResponseModel<List<EventDonation>>>?,
                t: Throwable?
            ) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<EventDonation>>>?,
                response: Response<CommonResponseModel<List<EventDonation>>>?
            ) {
                if (response?.code() == 200) {
                    Log.d("tes2", "Res 200")
                    val eventResponse = response.body()
                    if (eventResponse?.statusCode == 200) {
                        val list = eventResponse.data
                        val eventAdapter = EventAdapter(list, 8)
                        home_rv_event.adapter = eventAdapter
                        eventAdapter.setOnItemClickCallBack(object :
                            EventAdapter.OnItemClickCallBack {
                            override fun onItemClicked(data: EventDonation) {
                                val intent = Intent(
                                    context,
                                    EventDetailPage::class.java
                                )
                                intent.putExtra(EventDetailPage.EXTRA_EVENT, data)
                                startActivity(intent)
                            }
                        })
                    }
                } else {
                    try {
                        val jObjError =
                            JSONObject(response!!.errorBody()?.string())
                        Toast.makeText(
                            context,
                            jObjError.getJSONObject("data").getString("error"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }

        })
        apiClient.getUser().enqueue(object : Callback<CommonResponseModel<List<User>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<User>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<User>>>?,
                response: Response<CommonResponseModel<List<User>>>?
            ) {
                if (response?.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse?.statusCode == 200) {
                        val list = userResponse.data
                        val df = DecimalFormat("#,###")
                        home_tv_point.text = df.format(list[0].points)
                    }

                } else {
                    Log.d("test2", "Code = ${response?.code().toString()}")
                }

            }

        })
    }

}
