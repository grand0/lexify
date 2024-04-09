package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentDiscoverBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.animator.StringAnimator
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.DiscoverViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private val viewModel: DiscoverViewModel by viewModels()

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    private var randomWordAnimator: StringAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.randomWordsState.collect {
                        randomWordAnimator?.cancel()

                        if (it.isNotEmpty()) {
                            randomWordAnimator = StringAnimator(
                                interpolator = StringInterpolator("%s", it.map { m -> m.word }, useBlankSpaceChar = true)
                            ) { text -> binding.tvRwWord.text = text }
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

        binding.clWotdBlock.setOnClickListener {
            if (viewModel.wordOfTheDayState.value.word.isNotEmpty()) {
                val word = viewModel.wordOfTheDayState.value.word
                val action = HomeFragmentDirections.actionHomeFragmentToWordFragment(word)
                navigator.navController.navigate(action)
            }
        }
        binding.clRwBlock.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRandomWordRouletteFragment()
            navigator.navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        randomWordAnimator?.cancel()
        _binding = null
        super.onDestroyView()
    }
}