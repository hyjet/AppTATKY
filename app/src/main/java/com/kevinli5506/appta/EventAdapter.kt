package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(val listEvent:List<EventDonation>, val limit:Int): RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }
    interface OnItemClickCallBack {
        fun onItemClicked(data: EventDonation)
    }
    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        var imgvEvent = itemView.item_event_img_promo
        var tvEventName = itemView.item_event_tv_name
        var tvEventDetail = itemView.item_event_tv_detail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(listEvent.size<limit){
            return listEvent.size
        }
        else{
            return limit
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventName.text = listEvent[position].name
        holder.tvEventDetail.text = listEvent[position].description
        val imageUrl ="${Constants.BASE_STORAGE_URL}${Constants.STORAGE_EVENTS_URL}${listEvent[position].imageFile}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(holder.imgvEvent)
        holder.itemView.setOnClickListener{
            onItemClickCallBack.onItemClicked(listEvent[holder.adapterPosition])
        }
    }
}