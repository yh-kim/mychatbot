package com.pickth.mychatbot.view.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pickth.mychatbot.R
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract
import com.pickth.mychatbot.view.main.adapter.data.Message
import com.pickth.mychatbot.view.main.adapter.holder.ChatViewHolder

/**
 * Created by Kim on 2017-05-24.
 */
class ChatAdapter(private val context: Context): RecyclerView.Adapter<ChatViewHolder>()
        , ChatAdapterContract.View, ChatAdapterContract.Model{

    companion object {
        val CHAT_TYPE_USER = 0
        val CHAT_TYPE_BOT_MESSAGE = 1
    }
    val arrRow = listOf<Int>(R.layout.item_chat_0, R.layout.item_chat_1)

    var onItemClickListener: OnItemClickListener ?= null

    val itemList: MutableList<Message> = ArrayList<Message>()

    override fun setOnClickListener(clickListener: OnItemClickListener) {
        onItemClickListener = clickListener
    }

    override fun getItemCount() = itemList.size

    override fun getItem(position: Int) = itemList.get(position)

    override fun addItem(item: Message) {
        itemList.add(item)
//        notifyDataSetChanged()
        notifyItemInserted(itemList.size - 1)
    }

    override fun getItemViewType(position: Int) = itemList.get(position).type

    override fun onBindViewHolder(holder: ChatViewHolder?, position: Int) {
        holder?.onBind(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater
                .from(parent?.context)
                .inflate(arrRow[viewType], parent, false)

        return ChatViewHolder(itemView, onItemClickListener)
    }

    override fun listSize(): Int = itemCount
}