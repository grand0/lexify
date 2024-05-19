package ru.kpfu.itis.ponomarev.lexify.presentation.transition

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Interpolator
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.Transition
import androidx.transition.TransitionValues
import ru.kpfu.itis.ponomarev.lexify.util.pxToSp

class ChangeTextSize(private val context: Context) : Transition() {

    private fun captureValues(values: TransitionValues) {
        values.values[PROPNAME_TEXT_SIZE] = (values.view as? TextView)?.textSize?.let {
            context.pxToSp(it)
        }
        values.values[PROPNAME_TEXT] = (values.view as? TextView)?.text
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
        val startView = startValues.view as? TextView
        val startTextSize = startValues.values[PROPNAME_TEXT_SIZE] as Float
        val endTextSize = endValues.values[PROPNAME_TEXT_SIZE] as Float
        if (startTextSize != endTextSize) {
            val animator = ValueAnimator.ofFloat(startTextSize, endTextSize)
            animator.addUpdateListener {
                view.textSize = it.getAnimatedValue() as Float
                view.text = (startValues.values[PROPNAME_TEXT] ?: view.text).toString()
            }
            return animator
        }
        return null
    }

    companion object {
        private const val PROPNAME_TEXT_SIZE = "ru.kpfu.itis.ponomarev.lexify.presentation.transition:change_text_size:text_size"
        private const val PROPNAME_TEXT = "ru.kpfu.itis.ponomarev.lexify.presentation.transition:change_text_size:text"
    }
}