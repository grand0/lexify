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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentListsBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.ListsListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.ListsDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.callback.ItemHorizontalSwipeCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog.CreateListBottomSheetDialogFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ItemListHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.ListsViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

@AndroidEntryPoint
class ListsFragment : BaseFragment() {

    private val listsViewModel: ListsViewModel by viewModels()

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ListsListAdapter(
            ListsDiffUtilItemCallback(),
            ::openList,
        )
        binding.rvLists.adapter = adapter

        val itemHorizontalSwipeCallback = ItemHorizontalSwipeCallback(
            requireContext(),
            mapOf(
                ItemListHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    right = ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.destroy)) { vh ->
                        val name = ItemListBinding.bind(vh.itemView).tvName.text.toString()
                        listsViewModel.deleteList(name)
                    }
                )
            ),
        )
        ItemTouchHelper(itemHorizontalSwipeCallback).attachToRecyclerView(binding.rvLists)

        binding.btnCreate.setOnClickListener {
            CreateListBottomSheetDialogFragment(
                createList = ::createList,
            ).show(childFragmentManager, null)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listsViewModel.listsState.collect {
                    if (it == null) {
                        binding.progressBar.isVisible = true
                        binding.rvLists.isVisible = false
                        binding.llEmptyList.isVisible = false
                    } else {
                        adapter.submitList(it)
                        binding.progressBar.isVisible = false
                        binding.rvLists.isVisible = true
                        binding.llEmptyList.isVisible = it.isEmpty()
                    }
                }
            }
        }
    }

    private fun createList(name: String): CompletableFuture<Boolean> {
        return listsViewModel.createList(name)
    }

    private fun openList(list: ListModel) {
        val extras = FragmentNavigatorExtras(
            binding.tvTitle to getString(R.string.lists_title_list_transition)
        )
        val action = ListsFragmentDirections.actionListsFragmentToListFragment(list.name)
        navigator.navController.navigate(action, extras)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}