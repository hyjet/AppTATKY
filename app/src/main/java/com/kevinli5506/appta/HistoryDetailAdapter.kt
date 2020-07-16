package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinli5506.appta.Model.OrderDetail
import kotlinx.android.synthetic.main.item_histroy_detail.view.*
import java.text.DecimalFormat

class HistoryDetailAdapter(val orderDetail: List<OrderDetail>) :
    RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val tvItem = itemView.item_history_order_detail_item
        val tvPrice = itemView.item_history_order_detail_price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_histroy_detail, parent, false)
        return HistoryDetailAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = "${orderDetail[position].recycleItem.itemType} (${orderDetail[position].amount} kg)"
        holder.tvItem.text = item
        val df = DecimalFormat("#,###")
        val price = orderDetail[position].recycleItem.price*orderDetail[position].amount
        val priceString = "Rp. ${df.format(price)}"
        holder.tvPrice.text = priceString
    }
}