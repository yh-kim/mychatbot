package com.pickth.mychatbot.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pickth.mychatbot.util.Dlog

/**
 * Created by yonghoon on 2017-06-16.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dlog.v("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Dlog.v("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Dlog.v("onRestart")
    }

    override fun onResume() {
        super.onResume()
        Dlog.v("onResume")
    }

    override fun onStop() {
        super.onStop()
        Dlog.v("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Dlog.v("onDestroy")
    }
}