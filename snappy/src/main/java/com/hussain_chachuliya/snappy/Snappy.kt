package com.hussain_chachuliya.snappy

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.CountDownTimer
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*


class Snappy(private val activity: Activity,
             private val colors: Array<Int>) {
    private var mCountDown: CountDownTimer? = null
    private var previousColor: Int? = null
    private var duration: Long = 1000
    private var mLinearLayout: LinearLayout? = null
    private var showStatusBarText: Boolean = false
    private var statusBarText: String = ""
    private var statusBarHeight: Int? = null

    fun setDuration(durationInMilliSecs: Long): Snappy {
        duration = durationInMilliSecs
        return this
    }

    fun showStatusBarText(value: Boolean): Snappy {
        showStatusBarText = value
        return this
    }

    fun setStatusBarHeight(value: Int): Snappy {
        statusBarHeight = value
        return this
    }

    fun setStatusBarText(value: String): Snappy {
        statusBarText = value
        return this
    }

    fun startBreathing(toolbar: Toolbar) {
        mCountDown = object : CountDownTimer(duration, duration) {
            override fun onFinish() {
                start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val random = Random()
                performColorTransition(toolbar,
                        ContextCompat.getColor(activity,
                                colors[random.nextInt((colors.size - 1) - 0 + 1) + 0]))
            }
        }.start()
        showTextInStatusBar(statusBarHeight, statusBarText)
    }

    fun stopBreathing(toolbar: Toolbar) {
        mCountDown?.cancel()
        performColorTransition(toolbar,
                ContextCompat.getColor(activity, getAttributeColor(activity, R.attr.colorPrimary)))
        hideTextInStatusBar()
    }

    private fun showTextInStatusBar(statusBarHeight: Int?, text: String) {
        if (!showStatusBarText) return
        statusBarHeight ?: return

        val type: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_SYSTEM_ERROR

        val parameters = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                statusBarHeight,
                type, // Allows the view to be on top of the StatusBar
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, // Keeps the button presses from going to the background window and Draws over status bar
                PixelFormat.TRANSLUCENT)
        parameters.gravity = Gravity.TOP or Gravity.CENTER

        val ll = LinearLayout(activity)
        ll.setBackgroundColor(Color.TRANSPARENT)
        val layoutParameters = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        ll.layoutParams = layoutParameters

        val tv = TextView(activity)
        val tvParameters = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        tv.layoutParams = tvParameters
        tv.setTextColor(Color.WHITE)
        tv.gravity = Gravity.CENTER
        tv.text = text
        ll.addView(tv)
        mLinearLayout = ll
        AnimateUtils.setAlphaAnimation(mLinearLayout!!, duration)

        val windowManager = activity.getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(mLinearLayout, parameters)
    }

    private fun hideTextInStatusBar() {
        if (!showStatusBarText) return
        val windowManager = activity.getSystemService(WINDOW_SERVICE) as WindowManager
        try {
            mLinearLayout?.let { windowManager.removeView(mLinearLayout) }
        } catch (e: IllegalArgumentException) {
            Log.e("SNAPPY", e.message)
        }

    }

    private fun performColorTransition(toolbar: Toolbar, colorTo: Int) {
        if (previousColor == null)
            previousColor = ContextCompat.getColor(activity, getAttributeColor(activity, R.attr.colorPrimary))

        val colorFrom = previousColor
        previousColor = colorTo

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = duration // milliseconds
        colorAnimation.addUpdateListener { animator ->
            toolbar.setBackgroundColor(animator.animatedValue as Int)
        }

        val statusBarAnimation = ValueAnimator.ofObject(ArgbEvaluator(),
                manipulateColor(colorFrom!!, 0.7f), manipulateColor(colorTo, 0.7f))
        statusBarAnimation.duration = duration
        statusBarAnimation.addUpdateListener { animator ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = animator.animatedValue as Int
            }
        }
        colorAnimation.start()
        statusBarAnimation.start()
    }

    // factor less than 1.0f to darken
    private fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255))
    }

    private fun getAttributeColor(context: Context, @AttrRes colorAttribute: Int): Int {
        val attrs = intArrayOf(colorAttribute)
        val ta = context.obtainStyledAttributes(attrs)
        /*Get the color resourceID that we want (the first index, and only item, in the
    attrs array). Use ContextCompat to get the color according to the theme.
    */
//        @ColorInt val color = ContextCompat.getColor(activity,
//                ta.getResourceId(0, -1))
//        // ALWAYS call recycle() on the TypedArray when you’re done.
//        ta.recycle()
//        return color
        return ta.getResourceId(0, -1)
    }
}