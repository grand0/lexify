package ru.kpfu.itis.ponomarev.lexify.presentation.animator

import android.animation.Keyframe
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator

class StringAnimator(
    private val interpolator: StringInterpolator,
    private val updateListener: (String) -> Unit,
) {

    private val valueAnimator: ValueAnimator

    init {
        interpolator.substitutions.let {
            val kfs = arrayOfNulls<Keyframe>(it.size * 2 + 1)
            for (i in it.indices) {
                kfs[i * 2] = Keyframe.ofFloat(i.toFloat() / it.size, i.toFloat())
                kfs[i * 2 + 1] = Keyframe.ofFloat((i.toFloat() + 0.5f) / it.size, i.toFloat())
            }
            kfs[kfs.lastIndex] = Keyframe.ofFloat(1f, it.size.toFloat())
            val pvh = PropertyValuesHolder.ofKeyframe("strPos", *kfs)
            valueAnimator = ValueAnimator.ofPropertyValuesHolder(pvh)
                .apply {
                    setDuration((it.size * 3000).toLong())
                    interpolator = LinearInterpolator()
                    repeatMode = ValueAnimator.RESTART
                    repeatCount = ValueAnimator.INFINITE
                    this.addUpdateListener { va ->
                        updateListener(this@StringAnimator.interpolator.interpolate((va.animatedValue as Float).toDouble()))
                    }
                    start()
                }
        }
    }

    fun cancel() {
        valueAnimator.apply {
            removeAllUpdateListeners()
            cancel()
        }
    }
}