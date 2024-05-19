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
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentLocalBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.animator.StringAnimator
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.LocalSpaceViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import ru.kpfu.itis.ponomarev.lexify.util.navigate
import javax.inject.Inject

@AndroidEntryPoint
class LocalSpaceFragment : BaseFragment() {

    private val localSpaceViewModel: LocalSpaceViewModel by viewModels()

    private var _binding: FragmentLocalBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    private var lovedValueAnimator: StringAnimator? = null
    private var listsValueAnimator: StringAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.clLovedBlock.setOnClickListener {
            val homeFragment = parentFragment as? HomeFragment
            var extras: Navigator.Extras? = null
            homeFragment?.let {
                extras = FragmentNavigatorExtras(
                    it.getAppTitleToTransitionNamePair(getString(R.string.app_title_loved_transition)),
                )
            }
            val action = HomeFragmentDirections.actionHomeFragmentToLovedFragment()
            navigator.navController.navigate(action, extras)
        }
        binding.clListsBlock.setOnClickListener {
            val homeFragment = parentFragment as? HomeFragment
            var extras: Navigator.Extras? = null
            homeFragment?.let {
                extras = FragmentNavigatorExtras(
                    it.getAppTitleToTransitionNamePair(getString(R.string.app_title_lists_transition)),
                )
            }
            val action = HomeFragmentDirections.actionHomeFragmentToListsFragment()
            navigator.navController.navigate(action, extras)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    localSpaceViewModel.recentLovedState.collect { words ->
                        lovedValueAnimator?.cancel()

                        if (words.isEmpty()) {
                            binding.tvLovedWord.text = getString(R.string.empty_text)
                            binding.tvLovedWord.setTextColor(requireContext().getColor(R.color.medium_emphasis))
                        } else {
                            binding.tvLovedWord.setTextColor(requireContext().getColor(R.color.high_emphasis))
                            lovedValueAnimator = StringAnimator(
                                interpolator = StringInterpolator(
                                    "%s",
                                    words,
                                    useBlankSpaceChar = true,
                                )
                            ) { binding.tvLovedWord.text = it }
                        }
                    }
                }
                launch {
                    localSpaceViewModel.recentListsState.collect { names ->
                        listsValueAnimator?.cancel()

                        if (names.isEmpty()) {
                            binding.tvListsWord.text = getString(R.string.empty_text)
                            binding.tvListsWord.setTextColor(requireContext().getColor(R.color.medium_emphasis))
                        } else {
                            binding.tvListsWord.setTextColor(requireContext().getColor(R.color.high_emphasis))
                            listsValueAnimator = StringAnimator(
                                interpolator = StringInterpolator(
                                    "%s",
                                    names,
                                    useBlankSpaceChar = true,
                                )
                            ) { binding.tvListsWord.text = it }
                        }
                    }
                }
            }
        }

        localSpaceViewModel.updateAll()
    }

    override fun onDestroyView() {
        lovedValueAnimator?.cancel()
        lovedValueAnimator = null
        listsValueAnimator?.cancel()
        listsValueAnimator = null
        _binding = null
        super.onDestroyView()
    }

}