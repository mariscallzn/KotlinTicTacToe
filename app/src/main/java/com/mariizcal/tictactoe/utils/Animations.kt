package com.mariizcal.tictactoe.utils

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator

/**
 * Created by andresmariscal on 19/01/17.
 */
class Animations {
    companion object {
        fun fadeIn(animationListener: Animation.AnimationListener?): Animation {
            val animation = AlphaAnimation(0f, 1f)
            animation.duration = 100
            animation.interpolator = LinearInterpolator()
            if (animationListener != null) animation.setAnimationListener(animationListener)
            return animation

        }
    }
}