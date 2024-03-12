package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentLocalBinding
import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import ru.kpfu.itis.ponomarev.lexify.presentation.animator.StringAnimator
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import javax.inject.Inject

@AndroidEntryPoint
class LocalSpaceFragment : Fragment() {

    private var _binding: FragmentLocalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var lovedService: LovedService

    private var lovedValueAnimator: StringAnimator? = null

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

        lifecycleScope.launch {
            val recentLoved = lovedService.getRecent(5)
            if (recentLoved.isEmpty()) {
                binding.tvLovedWord.text = getString(R.string.empty_text)
                binding.tvLovedWord.setTextColor(requireContext().getColor(R.color.gray))
            } else {
                lovedValueAnimator = StringAnimator(
                    interpolator = StringInterpolator(
                        "%s",
                        recentLoved.map { it.word },
                        useBlankSpaceChar = true,
                    )
                ) { binding.tvLovedWord.text = it }
            }
        }
    }

    override fun onDestroyView() {
        lovedValueAnimator?.cancel()
        _binding = null
        super.onDestroyView()
    }

}