package com.pickth.mychatbot.view.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract
import com.pickth.mychatbot.view.main.adapter.data.ChatData
import com.pickth.mychatbot.view.main.adapter.holder.ChatViewHolder

/**
 * Created by Kim on 2017-05-24.
 */
class ChatAdapter(private val context: Context): RecyclerView.Adapter<ChatViewHolder>()
        , ChatAdapterContract.View, ChatAdapterContract.Model{

    var onItemClickListener: OnItemClickListener ?= null

    val itemList: MutableList<ChatData> = ArrayList<ChatData>()

    override fun setOnClickListener(clickListener: OnItemClickListener) {
        onItemClickListener = clickListener
    }

    override fun getItemCount() = itemList.size

    override fun getItem(position: Int) = itemList.get(position)

    override fun addItem(item: ChatData) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ChatViewHolder?, position: Int) {
        holder?.onBind(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ChatViewHolder(context, parent, onItemClickListener)

}