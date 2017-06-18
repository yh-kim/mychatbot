package com.pickth.mychatbot.util

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by yonghoon on 2017-06-18.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 */
class FontManager {
    companion object {
        fun setGlobalFont(context: Context, view: View) {
            if (view is ViewGroup)
                for (i in 0..view.childCount - 1) {
                    val v = view.getChildAt(i)
                    setViewFont(context, v)

                    setGlobalFont(context, v)
                }
        }

        fun setViewFont(context: Context, view: View) {
            if(view is TextView) {
                view.typeface = Typeface.createFromFile("/system/fonts/DroidSans.ttf")
                Dlog.v("Set font. id : ${view.id}")
            }
        }
    }
}