package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinli5506.appta.Model.History
import com.kevinli5506.appta.Model.NotificationResponse
import kotlinx.android.synthetic.main.item_notification.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(val list : List<NotificationResponse>):
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: NotificationResponse)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvNotificationTitle = itemView.notification_list_title
        val tvNotificationDetail = itemView.notification_list_detail
        val tvNotificationDate = itemView.notification_list_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNotificationTitle.text = list[position].notification[0].title
        holder.tvNotificationDetail.text = list[position].notification[0].message
        val StDformatter = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH)
        val date: Date =StDformatter.parse(list[position].dateString)
        val DtSformatter = SimpleDateFormat("dd MMMM YYYY", Locale.ENGLISH)
        val dateString = DtSformatter.format(date)
        holder.tvNotificationDate.text = dateString

        holder.itemView.setOnClickListener{
            onItemClickCallBack.onItemClicked(list[holder.adapterPosition])
        }
    }
}