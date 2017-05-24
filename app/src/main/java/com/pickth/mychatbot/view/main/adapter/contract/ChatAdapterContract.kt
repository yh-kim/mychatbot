package com.pickth.mychatbot.view.main.adapter.contract

import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.data.ChatData

/**
 * Created by Kim on 2017-05-24.
 */
interface ChatAdapterContract {
    interface View {
        fun setOnClickListener(clickListener: OnItemClickListener)
    }

    interface Model {
        fun getItem(position: Int): ChatData

        fun addItem(item: ChatData)
    }
}