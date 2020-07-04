package com.kevinli5506.appta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevinli5506.appta.Model.SpecialPromo
import kotlinx.android.synthetic.main.item_special_promo.view.*

class SpecialPromoAdapter(val listSpecialPromo: List<SpecialPromo>, val limit: Int) :
    RecyclerView.Adapter<SpecialPromoAdapter.ViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: SpecialPromo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_special_promo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (listSpecialPromo.size < limit)
            return listSpecialPromo.size
        else
            return limit
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameSpecialPromo.text = listSpecialPromo[position].name
        holder.detailSpecialPromo.text = listSpecialPromo[position].detail
        Glide.with(holder.itemView.context)
            .load(listSpecialPromo[position].image)
            .into(holder.imgvSpecialPromo)
        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(listSpecialPromo[holder.adapterPosition])
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgvSpecialPromo = itemView.item_special_promo_img_promo
        var nameSpecialPromo = itemView.item_special_promo_tv_name
        var detailSpecialPromo = itemView.item_special_promo_tv_detail
    }
}