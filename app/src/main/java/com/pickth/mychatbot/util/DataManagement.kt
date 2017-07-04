package com.pickth.mychatbot.util

import android.content.Context

/**
 * Created by yonghoon on 2017-07-04.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class DataManagement {
    companion object {
        fun setAppPreferences(context: Context, key: String, value: String) {
            val pref = context.getSharedPreferences("chat", 0)
            val prefEditor = pref.edit()
            prefEditor.putString(key, value)
                    .commit()
        }

        fun getAppPreferences(context: Context, key: String): String {
            val prefEditor = context.getSharedPreferences("chat", 0)
            return prefEditor.getString(key, "")
        }
    }
}