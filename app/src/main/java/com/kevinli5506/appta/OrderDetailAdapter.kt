package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinli5506.appta.Model.OrderListResponse
import com.kevinli5506.appta.Model.OrderProducts
import kotlinx.android.synthetic.main.item_order_detail.view.*
import java.text.DecimalFormat

class OrderDetailAdapter(val list : List<OrderProducts>): RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val tvItem = itemView.item_order_detail_item
        val tvPrice = itemView.item_order_detail_price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = "${list[position].recycleItem.itemType} (${list[position].amount} kg)"
        holder.tvItem.text = item
        val df = DecimalFormat("#,###")
        val price = list[position].recycleItem.price*list[position].amount
        val priceString = "Rp. ${df.format(price)}"
        holder.tvPrice.text = priceString
    }
}