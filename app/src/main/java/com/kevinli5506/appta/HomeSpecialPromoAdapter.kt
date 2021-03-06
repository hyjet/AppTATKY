package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_special_promo.view.*

class HomeSpecialPromoAdapter (val listSpecialPromo :ArrayList<SpecialPromo>): RecyclerView.Adapter<HomeSpecialPromoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_special_promo,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listSpecialPromo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameSpecialPromo.text=listSpecialPromo[position].name
        holder.detailSpecialPromo.text=listSpecialPromo[position].detail
        Glide.with(holder.itemView.context)
            .load(listSpecialPromo[position].image)
            .into(holder.imgvSpecialPromo)
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var imgvSpecialPromo = itemView.item_special_promo_img_promo
        var nameSpecialPromo = itemView.item_special_promo_tv_name
        var detailSpecialPromo = itemView.item_special_promo_tv_detail
    }
}