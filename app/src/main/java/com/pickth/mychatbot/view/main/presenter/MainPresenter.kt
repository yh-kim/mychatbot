package com.pickth.mychatbot.view.main.presenter

import android.content.Context
import android.widget.Toast
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract
import com.pickth.mychatbot.view.main.adapter.data.ChatData

/**
 * Created by Kim on 2017-05-24.
 */
class MainPresenter: MainContract.Presenter {
    var context: Context? = null
    var view: MainContract.View? = null
    var chatView: ChatAdapterContract.View? = null
    var chatModel: ChatAdapterContract.Model? = null

    /**
     * 어댑터 뷰 설정
     */
    override fun setChatAdapterView(chatView: ChatAdapterContract.View) {
        this.chatView = chatView
    }

    /**
     * 어댑터 모델 설정
     */
    override fun setChatAdapterModel(chatModel: ChatAdapterContract.Model) {
        this.chatModel = chatModel
    }

    /**
     * 메인 뷰 설정
     */
    override fun attachView(view: MainContract.View, context: Context) {
        this.view  = view
        this.context = context
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "$position 번째 메시지를 누르셨습니다", Toast.LENGTH_SHORT).show()
    }

    fun testInputItem(text: String) {
        chatModel?.addItem(ChatData(text,0))
    }
}