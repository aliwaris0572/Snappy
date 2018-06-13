package com.hussain_chachuliya.snappy

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import java.util.*


class Snappy(private val context: Activity,
             private val colors: Array<Int>) {
    private var mCountDown: CountDownTimer? = null
    private var previousColor: Int? = null
    private val duration: Long = 1000

    fun startBreathing(toolbar: Toolbar) {
        mCountDown = object : CountDownTimer(duration, duration) {
            override fun onFinish() {
                start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val random = Random()
                performColorTransition(toolbar,
                        ContextCompat.getColor(context,
                                colors[random.nextInt((colors.size - 1) - 0 + 1) + 0]))
            }

        }.start()
    }

    fun stopBreathing(toolbar: Toolbar) {
        mCountDown?.cancel()
        performColorTransition(toolbar,
                ContextCompat.getColor(context, getAttributeColor(context, R.attr.colorPrimary)))
    }

    fun performColorTransition(toolbar: Toolbar, colorTo: Int) {
        if (previousColor == null)
            previousColor = ContextCompat.getColor(context, getAttributeColor(context, R.attr.colorPrimary))

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
                context.window.statusBarColor = animator.animatedValue as Int
            }
        }
        colorAnimation.start()
        statusBarAnimation.start()
    }

    // factor less than 1.0f to darken
    fun manipulateColor(color: Int, factor: Float): Int {
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
//        @ColorInt val color = ContextCompat.getColor(context,
//                ta.getResourceId(0, -1))
//        // ALWAYS call recycle() on the TypedArray when you’re done.
//        ta.recycle()
//        return color
        return ta.getResourceId(0, -1)
    }
}