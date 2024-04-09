package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentSearchBinding
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (v.text.isNotBlank() && (event?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH)) {
                val word = v.text.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToWordFragment(word)
                navigator.navController.navigate(action)
            }
            true
        }
        binding.etSearch.setOnLongClickListener {
            binding.etSearch.setText("")
            binding.etSearch.requestFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etSearch, 0)
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}