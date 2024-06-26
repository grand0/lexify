package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.get
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentRandomWordRouletteBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.model.RandomWordsUiModel
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.RandomWordRouletteViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.dpToPx
import ru.kpfu.itis.ponomarev.lexify.util.navigate
import javax.inject.Inject

@AndroidEntryPoint
class RandomWordRouletteFragment : BaseFragment() {

    private val viewModel: RandomWordRouletteViewModel by viewModels()

    private var _binding: FragmentRandomWordRouletteBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    private var valueAnimator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomWordRouletteBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progressBar.isGone = false
        binding.vSelector.isGone = true
        binding.tvMessage.isGone = true

        binding.scrollView.setOnTouchListener { _, _ -> true }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomWordsState.collect { uiModel ->
                    when (uiModel) {
                        RandomWordsUiModel.Error -> {
                            Snackbar.make(binding.root, R.string.roulette_error_text, Snackbar.LENGTH_SHORT)
                                .show()
                            navigator.navController.navigateUp()
                        }
                        RandomWordsUiModel.Loading -> { /* no-op */ }
                        is RandomWordsUiModel.Ok -> {
                            startRoulette(uiModel.words)
                        }
                    }

                }
            }
        }

        viewModel.updateRandomWords()
    }

    private fun startRoulette(models: List<RandomWordModel>) {
        if (models.size < 3) return

        binding.progressBar.isGone = true
        binding.vSelector.isGone = false
        binding.tvMessage.isGone = false

        valueAnimator?.cancel()
        binding.llWords.removeAllViews()
        var winningTextView: TextView? = null
        models.mapIndexed { index, model ->
            TextView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    context.dpToPx(90)
                )
                textSize = 36f
                gravity = Gravity.CENTER
                text = model.word
                if (index == models.lastIndex - WINNING_WORD_INDEX_FROM_END) {
                    transitionName = getString(R.string.word_roulette_transition)
                    winningTextView = this
                }
            }
        }.forEach { tv ->
            binding.llWords.addView(tv)
        }
        repeat(5) {
            binding.llWords.addView(
                View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        context.dpToPx(90)
                    )
                },
                0,
            )
        }
        binding.scrollView.post {
            valueAnimator = ValueAnimator.ofInt(0, binding.scrollView.getChildAt(0).measuredHeight - binding.scrollView.measuredHeight).apply {
                setDuration(5000)
                interpolator = DecelerateInterpolator()
                addUpdateListener { va ->
                    binding.scrollView.scrollY = va.animatedValue as Int
                }
                this.addListener(object : AnimatorListener {
                    override fun onAnimationEnd(animation: Animator) {
                        val word = models[models.lastIndex - WINNING_WORD_INDEX_FROM_END].word
                        val action = RandomWordRouletteFragmentDirections.actionRandomWordRouletteFragmentToWordFragment(word)
                        NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, false)
                            .build().let { navOptions ->
                                val extrasPairs = mutableListOf<Pair<View, String>>()
                                winningTextView?.let {
                                    extrasPairs.add(it to getString(R.string.word_word_transition))
                                }
                                val extras = FragmentNavigatorExtras(*extrasPairs.toTypedArray())
                                navigator.navController.navigate(action, navOptions, extras)
                            }
                    }

                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
            valueAnimator?.start()
        }
    }

    override fun onDestroyView() {
        valueAnimator?.apply {
            removeAllListeners()
            cancel()
        }
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val WINNING_WORD_INDEX_FROM_END = 3
    }
}