package com.pickth.mychatbot.view.main.presenter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.pickth.commons.extensions.toast
import com.pickth.mychatbot.util.Dlog
import com.pickth.mychatbot.util.OnItemClickListener
import com.pickth.mychatbot.view.main.adapter.ChatAdapter
import com.pickth.mychatbot.view.main.adapter.contract.ChatAdapterContract
import com.pickth.mychatbot.view.main.adapter.data.Message

/**
 * Created by Kim on 2017-05-24.
 */
class MainPresenter: MainContract.Presenter, OnItemClickListener {

    lateinit var context: Context
    lateinit var view: MainContract.View
    lateinit var chatView: ChatAdapterContract.View
    lateinit var chatModel: ChatAdapterContract.Model

    /**
     * 어댑터 뷰 설정
     */
    override fun setChatAdapterView(chatView: ChatAdapterContract.View) {
        this.chatView = chatView

        this.chatView.setOnClickListener(this)
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
        this.view = view
        this.context = context
    }

    fun testInputItem(text: String) {
        if(text == "") {
            (this.context as Activity).toast("텍스트를 입력해주세요.")
//            this.view?.showToast("텍스트를 입력해주세요.")
            return
        }

        chatModel.addItem(Message(text, ChatAdapter.CHAT_TYPE_USER))
        chatModel.addItem(Message("봇의 답변입니다", ChatAdapter.CHAT_TYPE_BOT_MESSAGE))
        Dlog.v("텍스트 입력")

        view.scrollToLastChat()
    }

    override fun itemClick(position: Int) {
        (this.context as Activity).toast("$position 번째 메시지를 누르셨습니다")
//        this.view?.showToast("$position 번째 메시지를 누르셨습니다")
        view.hideSoftKeyboard()
    }

    override fun itemLongClick(position: Int) {
        view.hideSoftKeyboard()
        val builder = AlertDialog.Builder(context)
        val items = arrayOf("delete", "cancel")
        builder.setItems(items, DialogInterface.OnClickListener {
            dialogInterface, i ->
            when(i) {
                0 -> {
                    // delete
                    chatModel.removeItem(position)
                }
            }
        })

        val dialog = builder.create()
        dialog.show()

    }

    override fun itemClickBackground() {
        view.hideSoftKeyboard()
    }
}