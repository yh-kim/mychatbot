package com.pickth.mychatbot.view.main.adapter.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pickth.mychatbot.R
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.data.ChatData
import kotlinx.android.synthetic.main.item_chat_1.view.*

/**
 * Created by Kim on 2017-05-24.
 */
class ChatViewHolder(val context: Context, parent: ViewGroup?, var onItemClickListener: OnItemClickListener?):
        RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_1, parent, false)){

    fun onBind(item: ChatData, position: Int) {
        with(itemView) {
            text_chat_context.text = item.msg
            text_chat_context.setOnClickListener {
                onItemClickListener?.itemClick(position)
            }
        }
    }
}