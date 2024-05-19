package ru.kpfu.itis.ponomarev.lexify.presentation.transition

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.Transition
import androidx.transition.TransitionValues

class ChangeTextColor : Transition() {

    private fun captureValues(values: TransitionValues) {
        values.values[PROPNAME_TEXT_COLOR] = (values.view as? TextView)?.currentTextColor
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }
        val view = endValues.view as? TextView ?: return null
        val startTextColor = startValues.values[PROPNAME_TEXT_COLOR] as Int
        val endTextColor = endValues.values[PROPNAME_TEXT_COLOR] as Int
        if (startTextColor != endTextColor) {
            val animator = ValueAnimator.ofArgb(startTextColor, endTextColor)
            animator.addUpdateListener {
                view.setTextColor(it.getAnimatedValue() as Int)
            }
            return animator
        }
        return null
    }

    companion object {
        private const val PROPNAME_TEXT_COLOR = "ru.kpfu.itis.ponomarev.lexify.presentation.transition:change_text_size:text_color"
    }
}