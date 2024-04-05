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
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentLocalBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.animator.StringAnimator
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.LocalSpaceViewModel
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import javax.inject.Inject

@AndroidEntryPoint
class LocalSpaceFragment : Fragment() {

    private val localSpaceViewModel: LocalSpaceViewModel by viewModels()

    private var _binding: FragmentLocalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navController: NavController

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
        binding.tvLovedWord.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLovedFragment()
            navController.navigate(action)
        }
        binding.tvListsWord.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListsFragment()
            navController.navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    localSpaceViewModel.recentLovedState.collect { words ->
                        lovedValueAnimator?.cancel()

                        if (words.isEmpty()) {
                            binding.tvLovedWord.text = getString(R.string.empty_text)
                            binding.tvLovedWord.setTextColor(requireContext().getColor(R.color.gray))
                        } else {
                            binding.tvLovedWord.setTextColor(requireContext().getColor(R.color.black))
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
                            binding.tvListsWord.setTextColor(requireContext().getColor(R.color.gray))
                        } else {
                            binding.tvListsWord.setTextColor(requireContext().getColor(R.color.black))
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