package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinli5506.appta.Model.OrderListResponse
import kotlinx.android.synthetic.main.item_order_detail_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter(val list: List<OrderListResponse>) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderDetailLocation = itemView.item_order_list_location
        val tvOrderDetailTypeAmount = itemView.item_order_list_item_type
        val tvOrderDetailDate = itemView.item_order_list_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val StDformatter = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH)
        val date: Date =StDformatter.parse(list[position].orderproducts[0].date)
        val DtSformatter = SimpleDateFormat("dd MMMM YYYY", Locale.ENGLISH)
        val dateString = DtSformatter.format(date)
        holder.tvOrderDetailDate.text = dateString
        holder.tvOrderDetailLocation.text = list[position].location
        val itemTypeAmountString = "${list[position].orderproducts.size.toString()} jeins barang"
        holder.tvOrderDetailTypeAmount.text = itemTypeAmountString
        holder.itemView.setOnClickListener{
            onItemClickCallBack.onItemClicked(holder.adapterPosition)
        }
    }
}