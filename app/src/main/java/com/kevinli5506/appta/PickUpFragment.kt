package com.kevinli5506.appta

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
    var count: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pick_up_rv_order.layoutManager = LinearLayoutManager(view.context)
        pick_up_rv_order.adapter = OrderAdapter(count)
        pick_up_btn_add_item.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.setOnClickListener(this)
        pick_up_imgv_btn_remove_item.isEnabled = false
    }

    override fun onClick(v: View?) {
        when (v) {
            pick_up_btn_add_item -> {
                count += 1
                pick_up_rv_order.adapter = OrderAdapter(count)
                Log.d("test",count.toString())
                pick_up_imgv_btn_remove_item.isEnabled = true
            }
            pick_up_imgv_btn_remove_item -> {
                count-=1
                pick_up_rv_order.adapter = OrderAdapter(count)
                Log.d("test",count.toString())
                if(count==1)
                    pick_up_imgv_btn_remove_item.isEnabled = false
            }

        }

    }

}
