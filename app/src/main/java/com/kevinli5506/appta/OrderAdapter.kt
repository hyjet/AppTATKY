package com.kevinli5506.appta

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter() :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    class OrderData(
        var typeOder: Int = 0,
        var amount: String = ""
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTypeList =
            holder.itemView.context.resources.getStringArray(R.array.item_type_pick_up)
        val arrayAdapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            itemTypeList
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.orderType.adapter = arrayAdapter
        holder.orderType.setSelection(orderData[position].typeOder)
        holder.orderType.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                a: Int,
                id: Long
            ) {
                orderData[position].typeOder = a
            }

        }
        holder.orderAmount.setText(orderData[position].amount)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderType = itemView.item_order_spinner_item_type
        val orderAmount = itemView.item_order_edt_amount

        init {
            orderAmount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    orderData[adapterPosition].amount = orderAmount.text.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })
        }
    }

    companion object {
        var orderData: ArrayList<OrderData> = arrayListOf()
    }
}