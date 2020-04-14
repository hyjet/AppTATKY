package com.kevinli5506.appta

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsNewsAdapter(val news:ArrayList<News>) :RecyclerView.Adapter<NewsNewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(news[position].image)
            .into(holder.imgvNews)
        holder.titleNews.text = news[position].title
        holder.ratingNews.text=news[position].rating.toString()
        val timePast = {
            val now = Calendar.getInstance()
            val dif:Int = ((now.timeInMillis - news[position].time.timeInMillis)/1000).toInt()
            val minute = 60
            val hour = 3600
            val day = 3600*24
            val week = day*7
            val formatter = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val timedif = when{
                dif<minute -> "a few seconds ago"
                dif<hour -> "${(dif/minute)} ${if(dif/minute==1) "minute" else "minutes"} ago"
                dif<day -> "${(dif/hour)} ${if(dif/hour==1) "hour" else "hours"} ago"
                dif<week -> {if (dif/(day)==1) "yesterday" else "${dif/(day)} days ago"}
                else ->formatter.format(news[position].time.time).toString()
            }
            timedif
        }
        holder.timeNews.text = timePast()
    }
    class ViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView) {
        val imgvNews = itemView.item_news_imgv_img
        val titleNews = itemView.item_news_tv_title
        val ratingNews = itemView.item_news_tv_rate
        val timeNews = itemView.item_news_tv_time
    }
}