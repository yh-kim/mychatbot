package com.pickth.mychatbot.extensions

import android.app.Activity
import android.widget.Toast

/**
 * Created by yonghoon on 2017-07-06.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
fun Activity.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    if(isOnMainThread()) {
        Toast.makeText(this, id, length).show()
    } else {
        runOnUiThread {
            Toast.makeText(this, id, length).show()
        }
    }
}

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    if(isOnMainThread()) {
        Toast.makeText(this, msg, length).show()
    } else {
        runOnUiThread {
            Toast.makeText(this, msg, length).show()
        }
    }
}