package com.pickth.mychatbot.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Build
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
 * "https://github.com/premnirmal/Magnet"
 */
class FloatingViewService: Service() {
    companion object {
        val FLOATING_VIEW_RESIZE_WIDTH = 60f
    }
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
    private lateinit var mButtonBitmap: Bitmap
    private lateinit var mCircleBitmap: Bitmap
    private lateinit var mLeftBitmap: Bitmap
    private lateinit var mRightBitmap: Bitmap
    private var mSize = 0

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Dlog.v("floating view 서비스 시작")

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null)

        // Specify the view position
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        // Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mFloatingView, params)

        with(mFloatingView) {
            // Initialize image view background
            mSize = resources.getDimension(R.dimen.floating_widget_size).toInt()
            initializeBitmap(mSize, mSize)
            imageView2.setImageBitmap(mButtonBitmap)

            imageView2.setOnTouchListener { view, motionEvent ->

                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y

                        touchX = motionEvent.rawX
                        touchY = motionEvent.rawY

                        moveAnimator.stop()
                        isBeingDrag = true

                        mButtonBitmap = mCircleBitmap
                        imageView2.setImageBitmap(mButtonBitmap)

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
                        val Xdiff = params.x - initialX
                        val Ydiff = params.y - initialY

                        if(Xdiff < 10 && Ydiff < 10) {
                            // click event
                            Dlog.v("Button click")
                        }

                        Dlog.v("param.x: ${params.x} , defaultDisplay.width: ${mWindowManager.defaultDisplay.width}, width: ${mSize}")
                        isBeingDrag = false
                        goToSide()
                        imageView2.setImageBitmap(mButtonBitmap)
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
//        Dlog.v("params x:${params.x}, y:${params.y}, initialX:${initialX} , initialY:${initialY} \t${touchX}\t${touchY}")

        // Update the layout with new X & Y coordinate
        mWindowManager.updateViewLayout(mFloatingView, params)
    }

    fun goToSide() {
        if(params.x < mWindowManager.defaultDisplay.width/2 - mSize /2 ) {
            moveAnimator.setPosition(0f, params.y.toFloat())
            Dlog.v("Move button to left")
            mButtonBitmap = mLeftBitmap
        } else {
            moveAnimator.setPosition((mWindowManager.defaultDisplay.width - mSize + FLOATING_VIEW_RESIZE_WIDTH).toFloat(), params.y.toFloat())
            Dlog.v("Move button to right")
            mButtonBitmap = mRightBitmap
        }
        Dlog.v("x: ${params.x}, y: ${params.y}")
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager.removeView(mFloatingView)
        Dlog.v("서비스 종료")
    }

    private fun initializeBitmap(width: Int, height: Int) {
        mCircleBitmap = getBitmap(R.drawable.floating_button)
        mLeftBitmap = resizeBitmap(getBitmap(R.drawable.floating_button), FLOATING_VIEW_RESIZE_WIDTH.toInt(), 0, width - FLOATING_VIEW_RESIZE_WIDTH.toInt(), height)
        mRightBitmap = resizeBitmap(getBitmap(R.drawable.floating_button), 0, 0, width - FLOATING_VIEW_RESIZE_WIDTH.toInt(), height)

        mButtonBitmap = mLeftBitmap
    }

    private fun getBitmap(drawableRes: Int): Bitmap {
        val drawable: Drawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = resources.getDrawable(drawableRes, null)
        } else {
            drawable = resources.getDrawable(drawableRes)
        }

        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)

        return bitmap
    }

    private fun resizeBitmap(bitmap: Bitmap, x: Int, y: Int, width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(bitmap, x, y, width, height)
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