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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentListBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordDefinitionItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.ListListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.ListDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.callback.ItemHorizontalSwipeCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ListWordDefinitionHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.ListViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.ClipboardUtil
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val listViewModel: ListViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator
    @Inject lateinit var clipboardUtil: ClipboardUtil

    private val args: ListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = args.listName

        val adapter = ListListAdapter(
            ListDiffUtilItemCallback(),
            requireContext(),
            wordClicked = { goToWord(it.word) }
        )
        binding.rvWords.adapter = adapter

        val itemHorizontalSwipeCallback = ItemHorizontalSwipeCallback(
            requireContext(),
            mapOf(
                ListWordDefinitionHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    left = ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.copy)) { vh ->
                        (vh as? ListWordDefinitionHolder)?.copyableText?.let {
                            adapter.notifyItemChanged(vh.bindingAdapterPosition)
                            clipboardUtil.copyText(it)
                        }
                    },
                    right = ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.forget)) { vh ->
                        (vh as? ListWordDefinitionHolder)?.item?.id?.let {
                            deleteDefinition(it)
                        }
                    }
                )
            )
        )
        ItemTouchHelper(itemHorizontalSwipeCallback).attachToRecyclerView(binding.rvWords)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.listDefinitionsState.collect { model ->
                    if (model == null) {
                        binding.rvWords.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.llEmptyList.isVisible = false
                    } else {
                        val items = mutableListOf<ListItemModel>()
                        model.forEach { (word, defs) ->
                            items.add(ListWordItemModel(word))
                            items.addAll(
                                defs.map {
                                    ListWordDefinitionItemModel(
                                        id = it.id,
                                        partOfSpeech = it.partOfSpeech,
                                        text = it.text,
                                        labels = it.labels,
                                    )
                                }
                            )
                        }
                        adapter.submitList(items)
                        binding.progressBar.isVisible = false
                        binding.rvWords.isVisible = true
                        binding.llEmptyList.isVisible = items.isEmpty()
                    }
                }
            }
        }

        listViewModel.updateListDefinitions(args.listName)
    }

    private fun goToWord(word: String) {
        val action = ListFragmentDirections.actionListFragmentToWordFragment(word)
        navigator.navController.navigate(action)
    }

    private fun deleteDefinition(id: String) {
        listViewModel.deleteDefinition(id, args.listName)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}