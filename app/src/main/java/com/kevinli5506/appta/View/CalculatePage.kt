package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.RecycleItem
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_calculate_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculatePage : BaseActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    var recyleItemList :ArrayList<RecycleItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_page)

        calculate_spin_item_type.onItemSelectedListener = this
        calculate_btn_calculate.setOnClickListener(this)

        refresh()



    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this)
        apiClient.getRecycleItemCategory().enqueue(object :Callback<CommonResponseModel<List<RecycleItem>>>{
            override fun onFailure(
                call: Call<CommonResponseModel<List<RecycleItem>>>?,
                t: Throwable?
            ) {
                Log.d("tes",t?.message)
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<RecycleItem>>>?,
                response: Response<CommonResponseModel<List<RecycleItem>>>?
            ) {
                if (response?.code() == 200) {
                    Log.d("tes2", "Res 200")
                    val recycleItemResponse = response.body()
                    if (recycleItemResponse.statusCode == 200) {
                        val list = recycleItemResponse.data
                        recyleItemList.addAll(list)
                        val typeList: ArrayList<String> = arrayListOf()
                        for (i in (0 until list.size)) {
                            typeList.add(list[i].itemType)
                        }
                        val adapter = ArrayAdapter(this@CalculatePage, android.R.layout.simple_spinner_item, typeList)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        calculate_spin_item_type.adapter = adapter
                    }
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when (v) {
            calculate_btn_calculate -> {
                if (!calculate_edt_weight.text.isNullOrEmpty()) {
                    val weight = calculate_edt_weight.text.toString().toInt()
                    val price = recyleItemList[calculate_spin_item_type.selectedItemPosition].price
                    val total = weight * price
                    calculate_edt_price.setText(total.toString())
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        calculate_edt_price.setText("")
    }

}
