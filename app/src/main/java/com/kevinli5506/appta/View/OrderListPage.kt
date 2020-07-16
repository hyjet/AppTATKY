package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.OrderListResponse
import com.kevinli5506.appta.Model.OrderListStatusResponse
import com.kevinli5506.appta.OrderListAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_order_list_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list_page)

        order_list_rv_list.layoutManager = LinearLayoutManager(this)
        refresh()
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }
    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getOrderList().enqueue(object : Callback<CommonResponseModel<OrderListStatusResponse>> {
            override fun onFailure(call: Call<CommonResponseModel<OrderListStatusResponse>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(this@OrderListPage, t?.message, Toast.LENGTH_SHORT)
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
                        if (generatedList.isNotEmpty() || notGeneratedList.isNotEmpty()) {
                            order_list_tv_empty_message.visibility = View.GONE
                            val allList :ArrayList<OrderListResponse> = arrayListOf()
                            allList.addAll(notGeneratedList)
                            allList.addAll(generatedList)
                            val historyAdapter = OrderListAdapter(allList)
                            order_list_rv_list.adapter = historyAdapter
                            historyAdapter.setOnItemClickCallBack(object :
                                OrderListAdapter.OnItemClickCallBack {
                                override fun onItemClicked(data: Int) {
                                    val intent =
                                        Intent(this@OrderListPage, OrderListDetailPage::class.java)
                                    intent.putExtra(OrderListDetailPage.EXTRAORDERLISTID, data)
                                    startActivity(intent)
                                }

                            })
                        } else {
                            order_list_tv_empty_message.text = getString(R.string.no_order_message)
                        }

                    }
                } else {
                    try {
                        val jObjError =
                            JSONObject(response!!.errorBody()?.string())
                        val arrayError =
                            jObjError.getJSONObject("data").getJSONArray("error")

                        Toast.makeText(
                            this@OrderListPage,
                            arrayError.getString(0),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                                this@OrderListPage,
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
