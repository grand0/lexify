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
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentLovedBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemLovedWordBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.LovedWordsSorting
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.LovedListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.LovedDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.callback.ItemHorizontalSwipeCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog.LovedWordsSortingBottomSheetDialogFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.LovedWordHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.LovedViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import javax.inject.Inject

@AndroidEntryPoint
class LovedFragment : Fragment() {

    private val lovedViewModel: LovedViewModel by viewModels()

    private var _binding: FragmentLovedBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLovedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSort.setOnClickListener {
            LovedWordsSortingBottomSheetDialogFragment(
                applySorting = ::changeSorting,
                currentSorting = lovedViewModel.currentSorting
            ).show(childFragmentManager, null)
        }

        val adapter = LovedListAdapter(LovedDiffUtilItemCallback(), ::onItemClicked)
        binding.rvWords.adapter = adapter

        val itemHorizontalSwipeCallback = ItemHorizontalSwipeCallback(
            requireContext(),
            mapOf(
                LovedWordHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    right = ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.unlove)) { vh ->
                        lovedViewModel.unlove(ItemLovedWordBinding.bind(vh.itemView).tvWord.text.toString())
                    }
                )
            )
        )
        ItemTouchHelper(itemHorizontalSwipeCallback).attachToRecyclerView(binding.rvWords)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                lovedViewModel.lovedState.collect {
                    adapter.submitList(it)
                    binding.llEmptyList.isVisible = it.isEmpty()
                }
            }
        }

        lovedViewModel.updateLoved()
    }

    private fun onItemClicked(model: LovedWordModel) {
        val action = LovedFragmentDirections.actionLovedFragmentToWordFragment(model.word)
        navigator.navController.navigate(action)
    }

    private fun changeSorting(sorting: LovedWordsSorting) {
        lovedViewModel.currentSorting = sorting
        lovedViewModel.updateLoved()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}