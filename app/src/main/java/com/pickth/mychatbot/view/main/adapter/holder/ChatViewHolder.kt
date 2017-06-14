package com.pickth.mychatbot.view.main.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.data.Message
import kotlinx.android.synthetic.main.item_chat_1.view.*

/**
 * Created by Kim on 2017-05-24.
 */
class ChatViewHolder(view: View?, var onItemClickListener: OnItemClickListener?): RecyclerView.ViewHolder(view) {

    fun onBind(item: Message, position: Int) {
        with(itemView) {
            text_chat_context.text = item.msg
            text_chat_context.setOnClickListener {
                onItemClickListener?.itemClick(position)
            }
        }
    }
}