package com.pickth.mychatbot.view.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.pickth.mychatbot.R
import com.pickth.mychatbot.base.BaseActivity
import com.pickth.mychatbot.service.FloatingViewService
import com.pickth.mychatbot.util.BackPressCloseHandler
import com.pickth.mychatbot.view.main.adapter.ChatAdapter
import com.pickth.mychatbot.view.main.presenter.MainContract
import com.pickth.mychatbot.view.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {
    companion object {
        private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    }
    var adapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION
            , Uri.parse("package:" + packageName))
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
        } else {
            // Initialize view
        }

        val presenter = MainPresenter()
        presenter.attachView(this,applicationContext)

        adapter = ChatAdapter(applicationContext)

        recycler_main_chat.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_main_chat.adapter = adapter

        presenter.setChatAdapterModel(adapter as ChatAdapter)
        presenter.setChatAdapterView(adapter as ChatAdapter)

        btn_main_submit.setOnClickListener {
            val editText = edit_main_input
            presenter.testInputItem(editText.text.toString())
            editText.text = null
        }
    }

    // MainContract 연결
    override fun startChatBotActivity(position: Int) {
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun scrollToLastChat() {
        recycler_main_chat.layoutManager.scrollToPosition(adapter?.itemCount!! -1)
    }

    override fun onBackPressed() {
        BackPressCloseHandler.getInstance()?.onBackPressed(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            // Check if the permission is granted or not
            if(resultCode == Activity.RESULT_OK) {
                // Initialize view
            } else {
                Toast.makeText(this, "Draw over other app permission not available. Closing the application", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initializeView() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_start_service -> startService(Intent(applicationContext, FloatingViewService::class.java))
            R.id.menu_stop_service -> stopService(Intent(applicationContext, FloatingViewService::class.java))
            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }
}
