package com.pickth.mychatbot.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pickth.mychatbot.util.DataManager
import com.pickth.mychatbot.util.Dlog

/**
 * Created by yonghoon on 2017-06-27.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class BootBroadcastReceiver: BroadcastReceiver() {
    companion object {
        val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1!!.action.equals(BOOT_ACTION)) {

            // If serviceStatus is null, status is ""
            val status = DataManager.getAppPreferences(p0!!, "serviceStatus")
            Dlog.v("Status of service is $status")

            if(status.equals("start") or status.equals("")) {
                val serviceIntent = Intent(p0, FloatingViewService::class.java)
                p0!!.startService(serviceIntent)
            }
        }
    }
}