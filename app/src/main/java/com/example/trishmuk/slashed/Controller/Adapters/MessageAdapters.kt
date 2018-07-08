package com.example.trishmuk.slashed.Controller.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.trishmuk.slashed.Controller.Models.Messages
import com.example.trishmuk.slashed.Controller.Services.DataServices
import com.example.trishmuk.slashed.R

class MessageAdapters(val context: Context, val message: ArrayList<Messages>): RecyclerView.Adapter<MessageAdapters.View_holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        val view = LayoutInflater.from(context).inflate(R.layout.messages_listview, parent, false)
        return View_holder(view)
    }

    override fun getItemCount(): Int {
        return message.count()
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        holder?.bindView(context, message[position])
    }

    inner class View_holder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val user_avatar = itemView?.findViewById<ImageView>(R.id.userAvatarImg)
        val user_name = itemView?.findViewById<TextView>(R.id.userNamelbl)
        val time_stamp = itemView?.findViewById<TextView>(R.id.timeStampLbl)
        val message_Body = itemView?.findViewById<TextView>(R.id.messageBodyLbl)

        fun bindView(context: Context, messages: Messages){
            val resourceId = context.resources.getIdentifier(messages.userAvatar, "drawable", context.packageName)
            user_avatar?.setImageResource(resourceId)
            user_avatar?.setBackgroundColor(DataServices.getBgColor(messages.avatarColor))
            user_name?.text = messages.u_name
            time_stamp?.text = messages.timestamp
            message_Body?.text = messages.message
        }
    }
}