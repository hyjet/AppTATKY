package com.kevinli5506.appta

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.kevinli5506.appta.Model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(val comments : List<Comment>):RecyclerView.Adapter<CommentAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name =comments[position].name
        val spaceIndex = name.indexOf(" ")
        var abbrive = name[0].toString()
        if (spaceIndex >= 0) {
            abbrive = abbrive+ name[spaceIndex+1]
        }
        val drawableName : TextDrawable = TextDrawable.builder()
            .beginConfig()
            .width(64)
            .height(64)
            .endConfig()
            .buildRound(abbrive,Color.RED)
        holder.imgvName.setImageDrawable(drawableName)
        holder.commenterName.text = comments[position].name
        holder.commentContent.text = comments[position].content
    }
    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        val imgvName = itemView.comment_imgv_name
        val commenterName = itemView.comment_tv_name
        val commentContent = itemView.comment_tv_comment
    }
}