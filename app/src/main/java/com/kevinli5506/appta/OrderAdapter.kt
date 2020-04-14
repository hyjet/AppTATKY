package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter(var count:Int):RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_order,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTypeList = holder.itemView.context.resources.getStringArray(R.array.item_type_pick_up)
        val arrayAdapter = ArrayAdapter(holder.itemView.context,android.R.layout.simple_spinner_item,itemTypeList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.orderType.adapter = arrayAdapter

    }
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val orderType = itemView.item_order_spinner_item_type
        val orderAmount = itemView.item_order_edt_amount
    }
}