package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentWordBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.presentation.base.BaseFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionNotFoundException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionRateLimitException
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelationshipTypeModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionErrorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionLoadingModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordAudioModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordEtymologyModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.helper.DictionarySectionHelper
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.DictionaryListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil.DictionaryDiffUtilItemCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.callback.ItemHorizontalSwipeCallback
import ru.kpfu.itis.ponomarev.lexify.presentation.view.decoration.HeaderItemDecoration
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.RelatedWordHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordDefinitionHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordEtymologyHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordExampleHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.WordViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.ClipboardUtil
import javax.inject.Inject

@AndroidEntryPoint
class WordFragment : BaseFragment() {

    private val viewModel: WordViewModel by viewModels()

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var navigator: AppNavigator
    @Inject lateinit var dictionarySectionHelper: DictionarySectionHelper
    @Inject lateinit var clipboardUtil: ClipboardUtil

    private val args: WordFragmentArgs by navArgs()

    private var dictionaryListAdapter: DictionaryListAdapter? = null
    private var wordDefinitionsItems: List<DictionaryItemModel>? = null
    private var wordEtymologiesItems: List<DictionaryItemModel>? = null
    private var wordRelatedItems: List<DictionaryItemModel>? = null
    private var wordExamplesItems: List<DictionaryItemModel>? = null
    private var wordAudioItems: List<DictionaryItemModel>? = null

    private var mediaPlayer: MediaPlayer? = null
    private var rateLimit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val word = args.word

        binding.tvWord.setOnClickListener {
            clipboardUtil.copyText(binding.tvWord.text)
        }

        binding.btnLove.setOnClickListener {
            if (viewModel.isLovedState.value) {
                viewModel.unlove(word)
                setLovedButtonState(false)
            } else {
                viewModel.love(word)
                setLovedButtonState(true)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDictionary.layoutManager = layoutManager
        dictionaryListAdapter = DictionaryListAdapter(
            DictionaryDiffUtilItemCallback(),
            requireContext(),
            ::onAudioPlayClickListener,
            ::openWord,
        ).also {
            binding.rvDictionary.adapter = it
            binding.rvDictionary.addItemDecoration(HeaderItemDecoration(it))
        }

        var isUserScrolling = false
        for (tab in dictionarySectionHelper.allTabNames()) {
            binding.tabs.addTab(
                binding.tabs.newTab().setText(tab)
            )
        }
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null && !isUserScrolling) {
                    val tabPos = tab.position
                    val section = DictionarySection.entries[tabPos]
                    val sectionPos = dictionaryListAdapter?.currentList?.indexOfFirst { it is DictionarySectionDividerModel && it.name == dictionarySectionHelper.sectionName(section) }
                    sectionPos?.let { layoutManager.scrollToPositionWithOffset(sectionPos, 0) }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected(tab)
            }
        })
        binding.rvDictionary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isUserScrolling = true
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isUserScrolling = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isUserScrolling) {
//                    val itemPosition = if (dy > 0) {
//                        layoutManager.findLastVisibleItemPosition()
//                    } else {
//                        layoutManager.findFirstVisibleItemPosition()
//                    }
                    val itemPosition = layoutManager.findFirstVisibleItemPosition()
                    val model: DictionarySectionDividerModel? =
                        dictionaryListAdapter?.currentList?.subList(0, itemPosition + 1)?.last { it is DictionarySectionDividerModel } as DictionarySectionDividerModel?
                    if (model != null) {
                        val tabPos = DictionarySection.entries.indexOfFirst { dictionarySectionHelper.sectionName(it) == model.name }
                        binding.tabs.getTabAt(tabPos)?.select()
                    }
                }
            }
        })

        val itemHorizontalSwipeCallback = ItemHorizontalSwipeCallback(
            requireContext(),
            mapOf(
                WordDefinitionHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    left = copySwipeAction { vh -> (vh as WordDefinitionHolder).copyableText},
                    right = ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.remember)) { vh ->
                        dictionaryListAdapter?.notifyItemChanged(vh.bindingAdapterPosition)
                        (vh as WordDefinitionHolder).item?.let {
                            addDefinitionToList(it)
                        }
                    }
                ),
                RelatedWordHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    left = copySwipeAction { vh -> (vh as RelatedWordHolder).word},
                ),
                WordEtymologyHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    left = copySwipeAction { vh -> (vh as WordEtymologyHolder).copyableText},
                ),
                WordExampleHolder::class to ItemHorizontalSwipeCallback.ItemHorizontalSwipeActions(
                    left = copySwipeAction { vh -> (vh as WordExampleHolder).copyableText},
                ),
            )
        )
        ItemTouchHelper(itemHorizontalSwipeCallback).attachToRecyclerView(binding.rvDictionary)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.isLovedState.collect { setLovedButtonState(it) } }
                launch { viewModel.definitionsState.collect { processDefinitions(it) } }
                launch { viewModel.etymologiesState.collect { processEtymologies(it) } }
                launch { viewModel.relatedWordsState.collect { processRelated(it) } }
                launch { viewModel.examplesState.collect { processExamples(it) } }
                launch { viewModel.audioState.collect { processAudio(it) } }
                launch {
                    for (error in viewModel.errorsChannel) {
                        processError(error)
                    }
                }
            }
        }

        if (!viewModel.isUpdated) {
            viewModel.update(word)
        }

        binding.tvWord.text = word
    }

    private fun addDefinitionToList(item: DictionaryWordDefinitionModel) {
        val model = WordDefinitionModel(
            id = item.id,
            partOfSpeech = item.partOfSpeech,
            attributionText = "",
            attributionUrl = "",
            text = item.text,
            labels = item.labels,
            word = args.word,
        )
        val action = WordFragmentDirections.actionWordFragmentToRememberDefinitionBottomSheetDialog(model)
        navigator.navController.navigate(action)
    }

    private fun setLovedButtonState(loved: Boolean) {
        binding.btnLove.apply {
            background = AppCompatResources.getDrawable(requireContext(), if (loved) R.drawable.btn_active else R.drawable.btn_normal)
            setTextColor(requireContext().getColor(if (loved) R.color.surface else R.color.primary))
            text = getString(if (loved) R.string.unlove else R.string.love_btn_text)
        }
    }

    private fun openWord(word: String) {
        val action = WordFragmentDirections.actionWordFragmentSelf(word)
        navigator.navController.navigate(action) // TODO: this breaks current word screen. need to add state saving/restoring
    }

    private fun processList() {
        val itemsList = mutableListOf<DictionaryItemModel>()
        var allLoading = true
        for (section in DictionarySection.entries) {
            val sectionItems = when (section) {
                DictionarySection.DEFINITIONS -> wordDefinitionsItems
                DictionarySection.ETYMOLOGIES -> wordEtymologiesItems
                DictionarySection.EXAMPLES -> wordExamplesItems
                DictionarySection.RELATED -> wordRelatedItems
                DictionarySection.AUDIO -> wordAudioItems
            }
            itemsList.add(DictionarySectionDividerModel(dictionarySectionHelper.sectionName(section)))
            if (sectionItems == null) {
                itemsList.add(DictionarySectionLoadingModel(section))
            } else {
                itemsList.addAll(sectionItems)
                allLoading = false
            }
        }
        dictionaryListAdapter?.submitList(itemsList)
        if (!allLoading) {
            binding.progressBar.isVisible = false
            binding.rvDictionary.isVisible = true
        } else {
            binding.progressBar.isVisible = true
            binding.rvDictionary.isVisible = false
        }
    }

    private fun processDefinitions(defs: List<WordDefinitionModel>?) {
        if (defs == null) {
            wordDefinitionsItems = null
        } else if (defs.isEmpty()) {
            wordDefinitionsItems = listOf()
        } else {
            val itemsList = mutableListOf<DictionaryItemModel>()
            defs
                .groupBy { it.attributionText }
                .mapKeys { group ->
                    DictionaryAttributionTextModel(
                        text = group.value.first().attributionText,
                        url = group.value.first().attributionUrl,
                    )
                }
                .mapValues { group ->
                    group.value.map {
                        DictionaryWordDefinitionModel(
                            id = it.id,
                            partOfSpeech = it.partOfSpeech,
                            text = it.text,
                            labels = it.labels,
                        )
                    }
                }
                .forEach { (attr, list) ->
                    itemsList.add(attr)
                    itemsList.addAll(list)
                }
            wordDefinitionsItems = itemsList
        }
        processList()
    }

    private fun processEtymologies(etyms: WordEtymologiesModel?) {
        wordEtymologiesItems = if (etyms?.text == null) {
            null
        } else {
            listOf(
                DictionaryWordEtymologyModel(etyms.text)
            )
        }
        processList()
    }

    private fun processExamples(list: List<WordExampleModel>?) {
        wordExamplesItems = list?.map {
            DictionaryWordExampleModel(
                id = it.id,
                text = it.text,
                url = it.url,
                title = it.title,
                author = it.author,
                year = it.year,
                word = it.word,
            )
        }
        processList()
    }

    private fun processRelated(list: List<RelatedWordsModel>?) {
        wordRelatedItems = list?.flatMap {
            mutableListOf<DictionaryItemModel>(
                DictionaryRelationshipTypeModel(type = it.relationshipType),
            ).apply {
                addAll(
                    it.words.map {
                        DictionaryRelatedWordModel(word = it)
                    }
                )
            }
        }
        processList()
    }

    private fun processAudio(list: List<WordAudioModel>?) {
        wordAudioItems = list?.map {
            DictionaryWordAudioModel(
                id = it.id,
                duration = it.duration,
                fileUrl = it.fileUrl,
                attributionText = it.attributionText,
                attributionUrl = it.attributionUrl,
            )
        }
        processList()
    }

    private fun processError(error: Throwable) {
        if (error is DictionarySectionException) {
            val msg = error.message
                ?: when (error) {
                    is DictionarySectionNotFoundException -> getString(R.string.found_nothing)
                    is DictionarySectionRateLimitException -> {
                        if (!rateLimit) {
                            rateLimit = true
                            Snackbar.make(
                                binding.root,
                                R.string.too_many_requests_message,
                                Snackbar.LENGTH_LONG,
                            )
                                .setBackgroundTint(requireContext().getColor(R.color.primary))
                                .show()
                        }
                        getString(R.string.rate_limit)
                    }

                    else -> getString(R.string.unknown_error)
                }
            val itemsList = listOf(
                DictionarySectionErrorModel(msg, error.section)
            )
            when (error.section) {
                DictionarySection.DEFINITIONS -> wordDefinitionsItems = itemsList
                DictionarySection.ETYMOLOGIES -> wordEtymologiesItems = itemsList
                DictionarySection.EXAMPLES -> wordExamplesItems = itemsList
                DictionarySection.RELATED -> wordRelatedItems = itemsList
                DictionarySection.AUDIO -> wordAudioItems = itemsList
            }
            processList()
        }
    }

    private fun onAudioPlayClickListener(url: String) {
        val mp = mediaPlayer?.apply {
            if (isPlaying) stop()
            reset()
        } ?: MediaPlayer()
        with (mp) {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                start()
            }
        }
    }

    private fun copySwipeAction(textProvider: (ViewHolder) -> String) =
        ItemHorizontalSwipeCallback.ItemSwipeAction(getString(R.string.copy)) { vh ->
            dictionaryListAdapter?.notifyItemChanged(vh.bindingAdapterPosition)
            clipboardUtil.copyText(textProvider(vh))
        }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}