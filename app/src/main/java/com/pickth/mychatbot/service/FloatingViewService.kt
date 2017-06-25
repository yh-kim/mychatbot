package com.pickth.mychatbot.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Handler
import android.os.IBinder
import android.os.Looper
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
    var touchX: Float = 0.0f
    var touchY: Float = 0.0f
    var params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
    )
    var isBeingDrag = false
    var moveAnimator = MoveAnimator()


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Dlog.v("floating view 서비스 시작")

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null)

        // Add the view to the window


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

                        touchX = motionEvent.rawX
                        touchY = motionEvent.rawY

                        moveAnimator.stop()
                        isBeingDrag = true
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if(isBeingDrag) {
//                            move(initialX + (motionEvent.rawX - touchX), initialY + (motionEvent.rawY - touchY))
                            move((motionEvent.rawX - touchX), (motionEvent.rawY - touchY))
                        }
                        touchX = motionEvent.rawX
                        touchY = motionEvent.rawY

                        true
                    }
                    MotionEvent.ACTION_UP -> {
//                        val Xdiff = (motionEvent.rawX - touchX).toInt()
//                        val Ydiff = (motionEvent.rawY - touchY).toInt()
                        val Xdiff = params.x - initialX
                        val Ydiff = params.y - initialY
                        if(Xdiff < 10 && Ydiff < 10) {
                            // click event
                        }

                        Dlog.v("param.x: ${params.x} , defaultDisplay.width: ${mWindowManager.defaultDisplay.width}, width: ${mFloatingView.width}")
                        isBeingDrag = false
                        goToSide()

                        true
                    }
                    else -> false

                }
            }
        }
    }

    fun move(x:Float, y:Float) {
        params.x += x.toInt()
        params.y += y.toInt()
        Dlog.v("params x:${params.x}, y:${params.y}, initialX:${initialX} , initialY:${initialY} \t${touchX}\t${touchY}")

        // Update the layout with new X & Y coordinate
        mWindowManager.updateViewLayout(mFloatingView, params)
    }

    fun goToSide() {
        if(params.x < mWindowManager.defaultDisplay.width/2 - mFloatingView.width/2 ) {
            moveAnimator.setPosition(0f, params.y.toFloat())
            Dlog.v("Move button to left")

        } else {
            moveAnimator.setPosition((mWindowManager.defaultDisplay.width - mFloatingView.width).toFloat(), params.y.toFloat())
            Dlog.v("Move button to right")

        }
        Dlog.v("x: ${params.x}, y: ${params.y}")
    }

    /*
    fun View.moveItem(x1:Int, x2: Int, y1: Int, y2: Int) {
        val animation = TranslateAnimation(x1.toFloat(), x2.toFloat(), y1.toFloat(), y2.toFloat())
        animation.duration = 10000
        Dlog.v("Start animation")
        startAnimation(animation)
    }
    */

    override fun onDestroy() {
        super.onDestroy()
        if(mFloatingView != null) mWindowManager.removeView(mFloatingView)
        Dlog.v("서비스 종료")
    }

    inner class MoveAnimator: Runnable {
        // get main thread looper
        private val handler = Handler(Looper.getMainLooper())
        private var destinationX: Float = 0f
        private var destinationY: Float = 0f
        var startingTime: Long = 0

        fun setPosition(x: Float, y: Float) {
            this.destinationX = x
            this.destinationY = y
            startingTime = System.currentTimeMillis();
            handler.post(this)
        }

        override fun run() {
            val progress = Math.min(1f, (System.currentTimeMillis() - startingTime) / 400f)
            var deltaX = (destinationX - params.x) * progress
            var deltaY = (destinationY - params.y) * progress
            move(deltaX, deltaY)
            if(progress < 1) {
                handler.post(this)
            }
        }

        fun stop() {
            handler.removeCallbacks(this)
        }
    }
}