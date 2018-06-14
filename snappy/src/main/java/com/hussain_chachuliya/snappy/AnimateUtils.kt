package com.hussain_chachuliya.snappy

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


class AnimateUtils {

    companion object {
        fun setAlphaAnimation(v: View, duration: Long) {
            val fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .3f)
            fadeOut.duration = duration
            val fadeIn = ObjectAnimator.ofFloat(v, "alpha", .3f, 1f)
            fadeIn.duration = duration

            val mAnimationSet = AnimatorSet()

            mAnimationSet.play(fadeIn).after(fadeOut)

            mAnimationSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    mAnimationSet.start()
                }
            })
            mAnimationSet.start()
        }
    }
}