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
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentRandomWordRouletteBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.RandomWordRouletteViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.dpToPx
import javax.inject.Inject

@AndroidEntryPoint
class RandomWordRouletteFragment : Fragment() {

    private val viewModel: RandomWordRouletteViewModel by viewModels()

    private var _binding: FragmentRandomWordRouletteBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    var valueAnimator: ValueAnimator? = null

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
                viewModel.randomWordsState.collect { models ->
                    if (models.size < 3) return@collect

                    binding.progressBar.isGone = true
                    binding.vSelector.isGone = false
                    binding.tvMessage.isGone = false

                    valueAnimator?.cancel()
                    binding.llWords.removeAllViews()
                    models.map { model ->
                        TextView(requireContext()).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                context.dpToPx(90)
                            )
                            textSize = 36f
                            gravity = Gravity.CENTER
                            text = model.word
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
                                    val word = models[models.lastIndex - 3].word
                                    val action = RandomWordRouletteFragmentDirections.actionRandomWordRouletteFragmentToWordFragment(word)
                                    NavOptions.Builder()
                                        .setPopUpTo(R.id.homeFragment, false)
                                        .build().let {
                                            navigator.navController.navigate(action, it)
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
            }
        }

        viewModel.updateRandomWords()
    }

    override fun onDestroyView() {
        valueAnimator?.cancel()
        _binding = null
        super.onDestroyView()
    }
}