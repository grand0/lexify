package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionNotFoundException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionRateLimitException
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionErrorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelationshipTypeModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordAudioModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordEtymologyModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.DictionaryListAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.WordViewModel

@AndroidEntryPoint
class WordFragment : Fragment() {

    private val viewModel: WordViewModel by viewModels()

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    private val args: WordFragmentArgs by navArgs()

    private var dictionaryListAdapter: DictionaryListAdapter? = null
    private var wordDefinitionsItems: List<DictionaryItemModel>? = null
    private var wordEtymologiesItems: List<DictionaryItemModel>? = null
    private var wordRelatedItems: List<DictionaryItemModel>? = null
    private var wordExamplesItems: List<DictionaryItemModel>? = null
    private var wordAudioItems: List<DictionaryItemModel>? = null

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

        var isUserScrolling = false
        for (tab in DictionarySection.entries) {
            binding.tabs.addTab(
                binding.tabs.newTab().setText(tab.tabName)
            )
        }
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null && !isUserScrolling) {
                    val tabPos = tab.position
                    val sectionName = DictionarySection.entries[tabPos].sectionName
                    val sectionPos = dictionaryListAdapter?.items?.indexOfFirst { it is DictionarySectionDividerModel && it.name == sectionName }
                    sectionPos?.let { (binding.rvDictionary.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(sectionPos, 0) }
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
                    val itemPosition = if (dy > 0) {
                        (binding.rvDictionary.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0
                    } else {
                        (binding.rvDictionary.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0
                    }
                    val section: DictionarySectionDividerModel? =
                        dictionaryListAdapter?.items?.subList(0, itemPosition + 1)?.last { it is DictionarySectionDividerModel } as DictionarySectionDividerModel?
                    if (section != null) {
                        val tabPos = DictionarySection.entries.indexOfFirst { it.sectionName == section.name }
                        binding.tabs.getTabAt(tabPos)?.select()
                    }
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.definitionsState.collect { processDefinitions(it) } }
                launch { viewModel.etymologiesState.collect { processEtymologies(it) } }
                launch { viewModel.relatedWordsState.collect { processRelated(it) } }
                launch { viewModel.examplesState.collect { processExamples(it) } }
                launch { viewModel.audioState.collect { processAudio(it) } }
                launch {
                    for (error in viewModel.errorsChannel) {
                        if (error is DictionarySectionException) {
                            val msg = error.message
                                ?: when (error) {
                                    is DictionarySectionNotFoundException -> getString(R.string.found_nothing)
                                    is DictionarySectionRateLimitException -> getString(R.string.rate_limit)
                                    else -> getString(R.string.unknown_error)
                                }
                            val itemsList = listOf(
                                DictionarySectionErrorModel(msg)
                            )
                            when (error.section) {
                                DictionarySection.DEFINITIONS -> wordDefinitionsItems = itemsList
                                DictionarySection.ETYMOLOGIES -> wordEtymologiesItems = itemsList
                                DictionarySection.EXAMPLES -> wordExamplesItems = itemsList
                                DictionarySection.RELATED -> wordRelatedItems = itemsList
                                DictionarySection.AUDIO -> wordAudioItems = itemsList
                            }
                        }
                    }
                }
            }
        }

        viewModel.update(word)

        binding.tvWord.text = word
    }

    private fun processList() {
        val itemsList = mutableListOf<DictionaryItemModel>()
        for (section in DictionarySection.entries) {
            itemsList.add(DictionarySectionDividerModel(section.sectionName))
            when (section) {
                DictionarySection.DEFINITIONS -> wordDefinitionsItems
                DictionarySection.ETYMOLOGIES -> wordEtymologiesItems
                DictionarySection.EXAMPLES -> wordExamplesItems
                DictionarySection.RELATED -> wordRelatedItems
                DictionarySection.AUDIO -> wordAudioItems
            }?.let { itemsList.addAll(it) }
        }
        dictionaryListAdapter = DictionaryListAdapter(itemsList, requireContext())
        binding.rvDictionary.swapAdapter(dictionaryListAdapter, true)
    }

    private fun processDefinitions(defs: List<WordDefinitionModel>?) {
        if (defs.isNullOrEmpty()) {
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
        wordEtymologiesItems = if (etyms == null) {
            listOf()
        } else {
            listOf(
                DictionaryWordEtymologyModel(etyms.text)
            )
        }
        processList()
    }

    private fun processExamples(list: List<WordExampleModel>?) {
        wordExamplesItems = if (list.isNullOrEmpty()) {
            listOf()
        } else {
            list.map {
                DictionaryWordExampleModel(
                    text = it.text,
                    url = it.url,
                    title = it.title,
                    author = it.author,
                    year = it.year,
                )
            }
        }
        processList()
    }

    private fun processRelated(list: List<RelatedWordsModel>?) {
        wordRelatedItems = if (list.isNullOrEmpty()) {
            listOf()
        } else {
            list.flatMap {
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
        }
        processList()
    }

    private fun processAudio(list: List<WordAudioModel>?) {
        wordAudioItems = if (list.isNullOrEmpty()) {
            listOf()
        } else {
            list.map {
                DictionaryWordAudioModel(
                    it.duration,
                    it.fileUrl,
                    it.attributionText,
                    it.attributionUrl,
                )
            }
        }
        processList()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}