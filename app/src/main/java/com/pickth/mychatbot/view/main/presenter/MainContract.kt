package com.pickth.mychatbot.view.main.presenter

import android.content.Context
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract

/**
 * Created by Kim on 2017-05-24.
 */
interface MainContract {
    interface View {
        fun startChatBotActivity(position: Int)

        fun showToast(text: String)
    }

    interface Presenter {
        fun attachView(view: View, context: Context)

        fun setChatAdapterModel(chatModel: ChatAdapterContract.Model)

        fun setChatAdapterView(chatView: ChatAdapterContract.View)
    }
}