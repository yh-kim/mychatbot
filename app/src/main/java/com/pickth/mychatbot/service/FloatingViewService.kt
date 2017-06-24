package com.pickth.mychatbot.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import com.pickth.mychatbot.R
import com.pickth.mychatbot.util.Dlog
import kotlinx.android.synthetic.main.floating_widget.view.*

/**
 * Created by yonghoon on 2017-06-22.
 * Blog   : http://blog.pickth.com
 * Github : https://github.com/yh-kim
 * Mail   : yonghoon.kim@pickth.com
 *
 * refer to "http://www.androidhive.info/2016/11/android-floating-widget-like-facebook-chat-head/"
 */
class FloatingViewService: Service() {
    private lateinit var mWindowManager: WindowManager
    private lateinit var mFloatingView: View
    var initialX: Int = 0
    var initialY: Int = 0
    var initialTouchX: Float = 0.0f
    var initialTouchY: Float = 0.0f

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Dlog.v("floating view 서비스 시작")

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null)

        // Add the view to the window
        var params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        )

        // Specify the view position
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        // Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mFloatingView, params)

        with(mFloatingView) {
            imageView2.setOnTouchListener { view, motionEvent ->

                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y

                        initialTouchX = motionEvent.rawX
                        initialTouchY = motionEvent.rawY
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (motionEvent.rawX - initialTouchX).toInt()
                        params.y = initialY + (motionEvent.rawY - initialTouchY).toInt()
                        Dlog.v("params x:${params.x}, y:${params.y}, initialX:${initialX} , initialY:${initialY} , \t${motionEvent.rawX}\t${motionEvent.rawY}\t${initialTouchX}\t${initialTouchY}")

                        // Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params)
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        val Xdiff = (motionEvent.rawX - initialTouchX).toInt()
                        val Ydiff = (motionEvent.rawY - initialTouchY).toInt()
                        if(Xdiff < 10 && Ydiff < 10) {
                            // click event
                        }
                        Dlog.v("param.x: ${params.x} , defaultDisplay.width: ${mWindowManager.defaultDisplay.width}, width: ${mFloatingView.width}")

                        if(params.x < mWindowManager.defaultDisplay.width/2 - mFloatingView.width/2 ) {
                            params.x = 0
                            Dlog.v("Move button to left")
                        } else {
                            params.x = mWindowManager.defaultDisplay.width - mFloatingView.width
                            Dlog.v("Move button to right")
                        }

                        mWindowManager.updateViewLayout(mFloatingView, params)
                        Dlog.v("x: ${params.x}, y: ${params.y}")
                        true
                    }
                    else -> false

                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(mFloatingView != null) mWindowManager.removeView(mFloatingView)
        Dlog.v("서비스 종료")
    }
}