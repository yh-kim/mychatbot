package com.pickth.mychatbot.util

import android.util.Log
import com.pickth.mychatbot.base.BaseApplication

/**
 * Created by yonghoon on 2017-06-15.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class Dlog {
    companion object {
        var TAG: String = "yonghoon"

        // Log level Error
        fun e(message: String) {
            if(BaseApplication.DEBUG)   Log.e(TAG, buildLogMsg(message))
        }

        // Log level Warning
        fun w(message: String) {
            if(BaseApplication.DEBUG)   Log.w(TAG, buildLogMsg(message))
        }

        // Log level Information
        fun i(message: String) {
            if(BaseApplication.DEBUG)   Log.i(TAG, buildLogMsg(message))
        }

        // Log level Debug
        fun d(message: String) {
            if(BaseApplication.DEBUG)   Log.d(TAG, buildLogMsg(message))
        }

        // Log level Verbose
        fun v(message: String) {
            if(BaseApplication.DEBUG)   Log.v(TAG, buildLogMsg(message))
        }

        private fun buildLogMsg(message: String): String {
            val ste = Thread.currentThread().stackTrace[4]
            val sb = StringBuilder()

            sb.append("[")
            sb.append(ste.fileName.replace(".java", ""))
            sb.append("::")
            sb.append(ste.methodName)
            sb.append("]")
            sb.append(message)

            return sb.toString()
        }
    }
}