package com.pickth.mychatbot.view.main.presenter

import android.content.Context
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract
import com.pickth.mychatbot.view.main.adapter.data.Message

/**
 * Created by Kim on 2017-05-24.
 */
class MainPresenter: MainContract.Presenter, OnItemClickListener {
    var context: Context? = null
    var view: MainContract.View? = null
    var chatView: ChatAdapterContract.View? = null
    var chatModel: ChatAdapterContract.Model? = null

    /**
     * 어댑터 뷰 설정
     */
    override fun setChatAdapterView(chatView: ChatAdapterContract.View) {
        this.chatView = chatView

        this.chatView?.setOnClickListener(this)
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

    override fun itemClick(position: Int) {
        this.view?.showToast("$position 번째 메시지를 누르셨습니다")
    }

    fun testInputItem(text: String) {
        chatModel?.addItem(Message(text,0))
        chatModel?.addItem(Message("봇의 답변입니다",1))

        view?.scrollToLastChat()
    }
}