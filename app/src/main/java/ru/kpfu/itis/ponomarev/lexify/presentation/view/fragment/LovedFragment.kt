package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentLovedBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemLovedWordBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.LovedWordsSorting
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.LovedListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.LovedDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.callback.ItemHorizontalSwipeCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog.LovedWordsSortingBottomSheetDialogFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.LovedWordHolder
import javax.inject.Inject

@AndroidEntryPoint
class LovedFragment : Fragment() {

    private var _binding: FragmentLovedBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var lovedService: LovedService
    private var adapter: LovedListAdapter? = null
    private var currentSorting = LovedWordsSorting.ALPHABETICALLY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLovedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSort.setOnClickListener {
            LovedWordsSortingBottomSheetDialogFragment(
                applySorting = ::changeSorting,
                currentSorting = currentSorting
            ).show(childFragmentManager, null)
        }

        adapter = LovedListAdapter(LovedDiffUtilItemCallback(), ::onItemClicked)
        binding.rvDictionary.adapter = adapter

        adapter?.let { adapter ->
            val itemHorizontalSwipeCallback = ItemHorizontalSwipeCallback(
                requireContext(),
                mapOf(
                    LovedWordHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                        right = ItemHorizontalSwipeCallback.ItemSwipeAction("unlove") { vh ->
                            lifecycleScope.launch {
                                lovedService.deleteWord(ItemLovedWordBinding.bind(vh.itemView).tvWord.text.toString())
                            }.invokeOnCompletion { e ->
                                if (e == null) {
                                    with (adapter.currentList.toMutableList()) {
                                        removeAt(vh.adapterPosition)
                                        adapter.submitList(this)
                                    }
                                } else {
                                    // something went wrong, notify adapter that the item is still there
                                    adapter.notifyItemChanged(vh.adapterPosition)
                                }
                            }
                        }
                    )
                )
            )
            ItemTouchHelper(itemHorizontalSwipeCallback).attachToRecyclerView(binding.rvDictionary)

            lifecycleScope.launch {
                val list = lovedService.getAll()
                adapter.submitList(list)
            }
        }
    }

    private fun onItemClicked(model: LovedWordModel) {
        val action = LovedFragmentDirections.actionLovedFragmentToWordFragment(model.word)
        navController.navigate(action)
    }

    private fun changeSorting(sorting: LovedWordsSorting) {
        lifecycleScope.launch {
            adapter?.submitList(lovedService.getAll(sorting))
            currentSorting = sorting
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}