package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinli5506.appta.Model.History
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(val histories: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: History)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHistoryCode = itemView.history_code
        val tvHistoryDate = itemView.history_date

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvHistoryCode.text = histories[position].title
        holder.tvHistoryDate.text = histories[position].dateString
        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(histories[holder.adapterPosition])
        }
    }
}