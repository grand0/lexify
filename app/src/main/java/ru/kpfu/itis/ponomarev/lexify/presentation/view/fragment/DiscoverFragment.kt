package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.animation.Keyframe
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentDiscoverBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.DiscoverViewModel
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private val viewModel: DiscoverViewModel by viewModels()

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navController: NavController

    private var valueAnimator: ValueAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var randomWordInterpolator: StringInterpolator?

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.randomWordsState.collect {
                        valueAnimator?.apply {
                            removeAllUpdateListeners()
                            cancel()
                        }

                        if (it.isNotEmpty()) {
                            randomWordInterpolator = StringInterpolator("%s", it.map { m -> m.word }, useBlankSpaceChar = true)
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
                                        binding.tvRwWord.text =
                                            randomWordInterpolator?.interpolate((va.animatedValue as Float).toDouble())
                                    }
                                    start()
                                }
                        }
                    }
                }
                launch {
                    viewModel.wordOfTheDayState.collect {
                        binding.tvWotdWord.text = it.word
                    }
                }
            }
        }

        binding.tvWotdWord.setOnClickListener {
            if (viewModel.wordOfTheDayState.value.word.isNotEmpty()) {
                val word = viewModel.wordOfTheDayState.value.word
                val action = HomeFragmentDirections.actionHomeFragmentToWordFragment(word)
                navController.navigate(action)
            }
        }
        binding.tvRwWord.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRandomWordRouletteFragment()
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        valueAnimator?.apply {
            removeAllUpdateListeners()
            cancel()
        }
        _binding = null
        super.onDestroyView()
    }
}