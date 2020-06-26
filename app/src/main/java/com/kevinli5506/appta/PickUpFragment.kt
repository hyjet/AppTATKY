package com.kevinli5506.appta

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_reward_detail_page.*
import kotlinx.android.synthetic.main.fragment_pick_up.*
import kotlinx.android.synthetic.main.fragment_pick_up.view.*


class PickUpFragment : Fragment(), View.OnClickListener {
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
        OrderAdapter.orderData.add(OrderAdapter.OrderData())
        pick_up_rv_order.layoutManager = LinearLayoutManager(view.context)
        pick_up_rv_order.adapter = OrderAdapter()
        pick_up_btn_add_item.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.isEnabled = false
        pick_up_edt_address.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            pick_up_edt_address->{
                val intent = Intent(context,LocationPickerActivity::class.java)
                startActivityForResult(intent,LocationPickerActivity.REQUEST_LOCATION_PICKER_CODE)
            }
            pick_up_btn_add_item -> {
                OrderAdapter.orderData.add(OrderAdapter.OrderData())
                pick_up_rv_order.adapter = OrderAdapter()
                pick_up_imgv_btn_remove_item.isEnabled = true
            }
            pick_up_imgv_btn_remove_item -> {
                OrderAdapter.orderData.add(OrderAdapter.OrderData())
                pick_up_rv_order.adapter = OrderAdapter()
                if (OrderAdapter.orderData.size == 1)
                    pick_up_imgv_btn_remove_item.isEnabled = false
            }
            pick_up_btn_pick_up->{
                val orderList :ArrayList<OrderAdapter.OrderData> = OrderAdapter.orderData
                val phone = pick_up_edt_phone.text.toString()
                val address = pick_up_edt_address.text.toString()
                val addressDescription = pick_up_edt_address_description.text.toString()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LocationPickerActivity.REQUEST_LOCATION_PICKER_CODE){
            if(resultCode==Activity.RESULT_OK){
                val address = data!!.getStringExtra(LocationPickerActivity.EXTRA_ADDRESS)
                val longitude = data.getDoubleExtra(LocationPickerActivity.EXTRA_LONGITUDE,0.0)
                val latitude = data.getDoubleExtra(LocationPickerActivity.EXTRA_LATITUDE,0.0)
                pick_up_edt_address.setText(address)
            }
        }

    }

}
