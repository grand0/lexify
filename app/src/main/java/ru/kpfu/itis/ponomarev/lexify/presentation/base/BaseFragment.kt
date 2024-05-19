package ru.kpfu.itis.ponomarev.lexify.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionSet
import ru.kpfu.itis.ponomarev.lexify.presentation.transition.ChangeTextColor
import ru.kpfu.itis.ponomarev.lexify.presentation.transition.ChangeTextSize

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeTextSize(requireContext()))
            addTransition(ChangeTextColor())
            addTransition(ChangeBounds())
        }
        enterTransition = Fade()
        exitTransition = Fade()
    }
}