package com.pickth.mychatbot.base

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager


/**
 * Created by yonghoon on 2017-06-15.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class BaseApplication: Application() {
    companion object {
        // 디버그 모드에 따라서 로그를 남기거나 남기지 않는다
        var DEBUG: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()

        DEBUG = isDebuggable(this)
    }

    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val pm = context.packageManager

        try {
            val appInfo = pm.getApplicationInfo(context.getPackageName(), 0)
            debuggable = (0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
        } catch (e: PackageManager.NameNotFoundException) {
            // debuggable = false
        }

        return debuggable
    }

}