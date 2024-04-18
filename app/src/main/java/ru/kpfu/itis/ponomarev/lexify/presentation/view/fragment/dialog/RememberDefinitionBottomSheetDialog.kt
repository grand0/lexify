package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.databinding.DialogChooseListBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.ListsSelectorListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.ListsSelectorDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.RememberDefinitionViewModel

@AndroidEntryPoint
class RememberDefinitionBottomSheetDialog : BottomSheetDialogFragment() {

    private val viewModel: RememberDefinitionViewModel by viewModels()
    private val args: RememberDefinitionBottomSheetDialogArgs by navArgs()

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

        viewModel.updateAllLists(args.def.id)
    }

    private fun onListSelected(item: ListSelectorModel) {
        viewModel.switch(args.def, item.name)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}