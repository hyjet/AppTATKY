package com.kevinli5506.appta.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinli5506.appta.LocationPickerActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.OrderRequest
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Model.RecycleItem
import com.kevinli5506.appta.OrderAdapter
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_pick_up.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PickUpFragment : Fragment(), View.OnClickListener {
    val itemTypes: ArrayList<String> = arrayListOf()
    var longitude = 0.0
    var latitude = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OrderAdapter.orderData.clear()
        OrderAdapter.orderData.add(
            OrderAdapter.OrderData()
        )
        pick_up_rv_order.layoutManager = LinearLayoutManager(view.context)
        refresh()

        pick_up_btn_add_item.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.setOnClickListener(this)
        pick_up_btn_pick_up.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.isEnabled = false
        pick_up_edt_address.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {

                val intent = Intent(
                    context,
                    LocationPickerActivity::class.java
                )
                startActivityForResult(
                    intent,
                    LocationPickerActivity.REQUEST_LOCATION_PICKER_CODE
                )
            }

            true
        }

    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(context!!)
        apiClient.getRecycleItemCategory().enqueue(object :
            Callback<CommonResponseModel<List<RecycleItem>>> {
            override fun onFailure(
                call: Call<CommonResponseModel<List<RecycleItem>>>?,
                t: Throwable?
            ) {
                Log.d("tes", t?.message)
                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<RecycleItem>>>?,
                response: Response<CommonResponseModel<List<RecycleItem>>>?
            ) {
                if (response?.code() == 200) {
                    Log.d("tes2", "Res 200")
                    val recycleItemResponse = response.body()
                    if (recycleItemResponse?.statusCode == 200) {
                        val list = recycleItemResponse.data

                        for (i in (0 until list.size)) {
                            itemTypes.add(list[i].itemType)
                        }
                        pick_up_rv_order.adapter = OrderAdapter(itemTypes)

                    }
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when (v) {
            pick_up_btn_add_item -> {
                OrderAdapter.orderData.add(
                    OrderAdapter.OrderData()
                )
                pick_up_rv_order.adapter = OrderAdapter(itemTypes)
                pick_up_imgv_btn_remove_item.isEnabled = true
            }
            pick_up_imgv_btn_remove_item -> {
                OrderAdapter.orderData.removeAt(
                    OrderAdapter.orderData.size - 1
                )
                pick_up_rv_order.adapter = OrderAdapter(itemTypes)
                if (OrderAdapter.orderData.size == 1)
                    pick_up_imgv_btn_remove_item.isEnabled = false
            }
            pick_up_btn_pick_up -> {
                pick_up_layout.isEnabled = false
                val orderList: ArrayList<OrderAdapter.OrderData> = OrderAdapter.orderData
                var total = 0f
                for (i in 0 until (orderList.size)) {
                    total += orderList[i].amount.toFloat()
                }
                if (total >= 3) {
                    val phone = pick_up_edt_phone.text.toString()
                    val address = pick_up_edt_address.text.toString()
                    val addressDescription = pick_up_edt_address_description.text.toString()
                    val apiClient = ApiClient.getApiService(context!!)
                    val orderRequest =
                        OrderRequest(
                            longitude,
                            latitude,
                            address,
                            addressDescription,
                            phone,
                            orderList
                        )
                    apiClient.postOrder(orderRequest)
                        .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                            override fun onFailure(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                t: Throwable?
                            ) {
                                Log.d("tes2", t?.message)
                                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
                                toast.show()
                                pick_up_layout.isEnabled = true
                            }

                            override fun onResponse(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                response: Response<CommonResponseModel<PostResponse>>?
                            ) {
                                if (response?.code() == 200) {
                                    val postResponse = response.body()
                                    if (postResponse?.statusCode == 200) {
                                        val message = postResponse.data.message
                                        Log.d("tes2", message)
                                        val toast = Toast.makeText(
                                            context,
                                            "Pickup Order Recieved",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.show()
                                        val thread = Thread {
                                            try {
                                                Thread.sleep(2000L)
                                                val intent = Intent(context, HomePage::class.java)
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                startActivity(intent)
                                            } catch (e: java.lang.Exception) {
                                                val toast2 = Toast.makeText(
                                                    context,
                                                    e.message,
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast2.show()
                                            }
                                        }
                                        thread.start()
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
                                    pick_up_layout.isEnabled = true
                                }
                            }

                        })
                }
                else{
                    Toast.makeText(context,"Maaf, kami hanya menerima pesanan minimal 3 kg",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationPickerActivity.REQUEST_LOCATION_PICKER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val address = data!!.getStringExtra(LocationPickerActivity.EXTRA_ADDRESS)
                longitude = data.getDoubleExtra(LocationPickerActivity.EXTRA_LONGITUDE, 0.0)
                latitude = data.getDoubleExtra(LocationPickerActivity.EXTRA_LATITUDE, 0.0)
                pick_up_edt_address.setText(address)
            }
        }

    }


}
