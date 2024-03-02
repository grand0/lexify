package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentWordBinding
import ru.kpfu.itis.ponomarev.lexify.util.ParamsKey

class WordFragment : Fragment() {

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val word = requireArguments().getString(ParamsKey.WORD_KEY)

        binding.tvWord.text = word
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}