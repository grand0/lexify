package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentDiscoverBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.animator.StringAnimator
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DiscoverScreenRandomWordException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DiscoverScreenWordOfTheDayException
import ru.kpfu.itis.ponomarev.lexify.presentation.model.RandomWordsUiModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.WordOfTheDayUiModel
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.DiscoverViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import ru.kpfu.itis.ponomarev.lexify.util.navigate
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : BaseFragment() {

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

                        when (it) {
                            RandomWordsUiModel.Error -> {
                                binding.tvRwWord.text = ""
                                binding.tvRwError.isVisible = true
                                binding.pbRw.isVisible = false
                                binding.vRwArrow.isVisible = false
                            }
                            RandomWordsUiModel.Loading -> {
                                binding.tvRwWord.text = ""
                                binding.tvRwError.isVisible = false
                                binding.pbRw.isVisible = true
                                binding.vRwArrow.isVisible = false
                            }
                            is RandomWordsUiModel.Ok -> {
                                binding.tvRwError.isVisible = false
                                binding.pbRw.isVisible = false
                                binding.vRwArrow.isVisible = true
                                if (it.words.isNotEmpty()) {
                                    randomWordAnimator = StringAnimator(
                                        interpolator = StringInterpolator("%s", it.words.map { m -> m.word }, useBlankSpaceChar = true)
                                    ) { text -> binding.tvRwWord.text = text }
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.wordOfTheDayState.collect {
                        when (it) {
                            WordOfTheDayUiModel.Error -> {
                                binding.tvWotdWord.text = ""
                                binding.tvWotdError.isVisible = true
                                binding.pbWotd.isVisible = false
                                binding.vWotdArrow.isVisible = false
                            }
                            WordOfTheDayUiModel.Loading -> {
                                binding.tvWotdWord.text = ""
                                binding.tvWotdError.isVisible = false
                                binding.pbWotd.isVisible = true
                                binding.vWotdArrow.isVisible = false
                            }
                            is WordOfTheDayUiModel.Ok -> {
                                binding.tvWotdWord.text = it.word.word
                                binding.tvWotdError.isVisible = false
                                binding.pbWotd.isVisible = false
                                binding.vWotdArrow.isVisible = true
                            }
                        }
                    }
                }
            }
        }

        binding.clWotdBlock.setOnClickListener {
            val wotd = viewModel.wordOfTheDayState.value
            when (wotd) {
                WordOfTheDayUiModel.Error -> viewModel.updateWordOfTheDay()
                WordOfTheDayUiModel.Loading -> { /* no-op */ }
                is WordOfTheDayUiModel.Ok -> {
                    val word = wotd.word.word
                    val homeFragment = parentFragment as? HomeFragment
                    var extras: Navigator.Extras? = null
                    homeFragment?.let {
                        extras = FragmentNavigatorExtras(
                            it.getAppTitleToTransitionNamePair(getString(R.string.app_title_word_transition)),
                        )
                    }
                    val action = HomeFragmentDirections.actionHomeFragmentToWordFragment(word)
                    navigator.navController.navigate(action, extras)
                }
            }
        }
        binding.clRwBlock.setOnClickListener {
            val rw = viewModel.randomWordsState.value
            when (rw) {
                RandomWordsUiModel.Error -> viewModel.updateRandomWords()
                RandomWordsUiModel.Loading -> { /* no-op */ }
                is RandomWordsUiModel.Ok -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToRandomWordRouletteFragment()
                    navigator.navController.navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        randomWordAnimator?.cancel()
        _binding = null
        super.onDestroyView()
    }
}