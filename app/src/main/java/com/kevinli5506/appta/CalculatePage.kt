package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_calculate_page.*

class CalculatePage : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    var recycleItemList: ArrayList<RecycleItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_page)
        recycleItemList.addAll(RecycleItemData.listRecycleItem)

        var typeList: ArrayList<String> = arrayListOf()
        for (i in (0 until recycleItemList.size)) {
            typeList.add(recycleItemList[i].itemType)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        calculate_spin_item_type.adapter = adapter
        calculate_spin_item_type.onItemSelectedListener = this
        calculate_btn_calculate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            calculate_btn_calculate -> {
                if (!calculate_edt_weight.text.isNullOrEmpty()) {
                    val weight = calculate_edt_weight.text.toString().toInt()
                    val price = recycleItemList[calculate_spin_item_type.selectedItemPosition].price
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
