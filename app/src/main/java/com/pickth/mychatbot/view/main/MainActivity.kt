package com.pickth.mychatbot.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.pickth.mychatbot.R
import com.pickth.mychatbot.view.main.adapter.ChatAdapter
import com.pickth.mychatbot.view.main.presenter.MainContract
import com.pickth.mychatbot.view.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainPresenter()
        presenter.attachView(this,applicationContext)

        val adapter = ChatAdapter(applicationContext)

        recycler_main_chat.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_main_chat.adapter = adapter

        presenter.setChatAdapterModel(adapter)
        presenter.setChatAdapterView(adapter)

        btn_main_submit.setOnClickListener {
            val editText = edit_main_input
            presenter.testInputItem(editText.text.toString())
            editText.text = null
        }
    }

    // MainContract 연결결
    override fun startChatBotActivity(position: Int) {
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
