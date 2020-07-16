package com.kevinli5506.appta.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.amulyakhare.textdrawable.TextDrawable
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.OrderListResponse
import com.kevinli5506.appta.Model.OrderListStatusResponse
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.OrderDetailAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_order_list_detail_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListDetailPage : BaseActivity(), View.OnClickListener {
    var id = 0
    var orderId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list_detail_page)
        id = intent.getIntExtra(EXTRAORDERLISTID, 0)
        order_detail_btn_cancel_order.setOnClickListener(this)
        order_detail_rview_order_detail.layoutManager = LinearLayoutManager(this)
        refresh()

    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getOrderList()
            .enqueue(object : Callback<CommonResponseModel<OrderListStatusResponse>> {
                override fun onFailure(
                    call: Call<CommonResponseModel<OrderListStatusResponse>>?,
                    t: Throwable?
                ) {
                    Log.d("tes2", t?.message)
                    val toast =
                        Toast.makeText(this@OrderListDetailPage, t?.message, Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<CommonResponseModel<OrderListStatusResponse>>?,
                    response: Response<CommonResponseModel<OrderListStatusResponse>>?
                ) {
                    if (response?.code() == 200) {
                        Log.d("tes2", "Res 200")
                        val orderListResponse = response.body()
                        if (orderListResponse?.statusCode == 200) {
                            val oresponse = orderListResponse.data
                            val generatedList = oresponse.generatedOrder.reversed()
                            val notGeneratedList = oresponse.notGeneratedOrder.reversed()
                            val allList: ArrayList<OrderListResponse> = arrayListOf()
                            allList.addAll(notGeneratedList)
                            allList.addAll(generatedList)
                            orderId = allList[id].id
                            if (allList[id].picker_name != null) {
                                val spaceIndex = allList[id].picker_name.indexOf(" ")
                                var abbrive = allList[id].picker_name
                                if (spaceIndex >= 0) {
                                    abbrive += allList[id].picker_name[spaceIndex + 1]
                                    val drawableName: TextDrawable = TextDrawable.builder()
                                        .beginConfig()
                                        .width(64)
                                        .height(64)
                                        .endConfig()
                                        .buildRound(abbrive, Color.RED)
                                    order_detail_cimgv_driver_photo.setImageDrawable(drawableName)
                                    order_detail_tv_driver_name.text = allList[id].picker_name
                                    order_detail_tv_driver_phone.text = allList[id].picker_phone
                                }
                            } else {
                                order_detail_tv_driver_not_decided_msg.visibility=View.VISIBLE
                                order_detail_tv_driver_name.visibility = View.GONE
                                oreder_detail_tv_driver_name_label.visibility = View.GONE
                                order_detail_tv_driver_phone.visibility = View.GONE
                                oreder_detail_tv_driver_phone_label.visibility = View.GONE
                            }

                            val orderDetailAdapter = OrderDetailAdapter(allList[id].orderproducts)
                            order_detail_rview_order_detail.adapter = orderDetailAdapter
                        }
                    } else {
                        try {
                            val jObjError =
                                JSONObject(response!!.errorBody()?.string())
                            val arrayError =
                                jObjError.getJSONObject("data").getJSONArray("error")

                            Toast.makeText(
                                this@OrderListDetailPage,
                                arrayError.getString(0),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                    this@OrderListDetailPage,
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
        val EXTRAORDERLISTID: String = "EXTRAORDERLISTID"
    }

    override fun onClick(v: View?) {
        when (v) {
            order_detail_btn_cancel_order -> {
                Log.d("tes2", "tapped")
                val apiClient = ApiClient.getApiService(this)
                apiClient.getCancelOrder(orderId.toString())
                    .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                        override fun onFailure(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                            val toast =
                                Toast.makeText(
                                    this@OrderListDetailPage,
                                    t?.message,
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            response: Response<CommonResponseModel<PostResponse>>?
                        ) {
                            if (response?.code() == 200) {
                                val postResponse = response.body()
                                if (postResponse?.statusCode == 200) {
                                    val message = postResponse.data.message
                                    val toast =
                                        Toast.makeText(
                                            this@OrderListDetailPage,
                                            message,
                                            Toast.LENGTH_SHORT
                                        )
                                    toast.show()
                                    finish()
                                }
                            } else {
                                try {
                                    val jObjError =
                                        JSONObject(response!!.errorBody()?.string())
                                    Toast.makeText(
                                        this@OrderListDetailPage,
                                        jObjError.getJSONObject("data").getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                            this@OrderListDetailPage,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            }
                        }

                    })
            }
        }
    }
}
