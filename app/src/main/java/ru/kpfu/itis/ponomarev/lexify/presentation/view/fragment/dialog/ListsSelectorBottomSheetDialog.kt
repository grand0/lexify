package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.databinding.DialogChooseListBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.ListsSelectorListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.ListsSelectorDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.ListsSelectorViewModel

@AndroidEntryPoint
class ListsSelectorBottomSheetDialog(
    private val definitionId: String,
    private val selectList: (ListSelectorModel) -> Unit,
) : BottomSheetDialogFragment() {

    private val viewModel: ListsSelectorViewModel by viewModels()

    private var _binding: DialogChooseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChooseListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ListsSelectorListAdapter(
            ListsSelectorDiffUtilItemCallback(),
            ::onListSelected,
        )
        binding.rvLists.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listSelectorsState.collect {
                    adapter.submitList(it)
                }
            }
        }

        viewModel.updateListSelectors(definitionId)
    }

    private fun onListSelected(item: ListSelectorModel) {
        selectList(item)
        viewModel.updateListSelectors(definitionId)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}