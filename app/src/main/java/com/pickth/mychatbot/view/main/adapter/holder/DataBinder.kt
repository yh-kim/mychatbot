package com.pickth.mychatbot.view.main.adapter.holder

import com.pickth.mychatbot.view.main.adapter.data.ChatData

/**
 * Created by Kim on 2017-05-24.
 */
interface DataBinder {
    fun onBind(item: ChatData, position: Int)
}