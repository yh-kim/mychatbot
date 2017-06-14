package com.pickth.mychatbot.util

import android.app.Activity
import android.os.Handler
import android.widget.Toast

/**
 * Created by yonghoon on 2017-06-11.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class BackPressCloseHandler {
    private var isBackPressedOnce = false

    companion object {
        @Volatile private var uniqueInstance: BackPressCloseHandler? = null

        fun getInstance(): BackPressCloseHandler? {
            if(uniqueInstance == null) {
                synchronized(this) {
                    if(uniqueInstance == null) {
                        uniqueInstance = BackPressCloseHandler()
                    }
                }
            }

            return uniqueInstance
        }
    }


    // 싱글톤 패턴에서는 context를 가지고 있는걸 조심해야 됨.
    // 액티비티가 종료된 이후에도 객체를 가지고 있어 OOM가 일어날 수 있음
    fun onBackPressed(context: Activity) {
        if(isBackPressedOnce) {
            context.finish()
        } else {
            Toast.makeText(context, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            isBackPressedOnce = true
            Handler().postDelayed(timerTask, 2*1000)
        }
    }

    private val timerTask = Runnable { isBackPressedOnce = false }
}