package com.kevinli5506.appta.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.OrderListResponse
import com.kevinli5506.appta.Model.OrderListStatusResponse
import com.kevinli5506.appta.OrderListAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_order_list.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order_list_rv_list.layoutManager = LinearLayoutManager(context)
        refresh()
    }
    override fun onStart() {
        super.onStart()
        refresh()
    }
    private fun refresh() {
        val apiClient = ApiClient.getApiService(context!!)
        apiClient.getOrderList().enqueue(object :
            Callback<CommonResponseModel<OrderListStatusResponse>> {
            override fun onFailure(call: Call<CommonResponseModel<OrderListStatusResponse>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
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
                                        Intent(context, OrderListDetailPage::class.java)
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
                            context,
                            arrayError.getString(0),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
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