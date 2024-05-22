package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemAttributionTextBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemRelationshipTypeTextBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionDividerBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionErrorBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionLoadingBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordAudioBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordDefinitionBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordEtymologyBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordExampleBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordRelatedBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelationshipTypeModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionErrorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionLoadingModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordAudioModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordEtymologyModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.decoration.HeaderItemDecoration
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.AttributionTextHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.RelatedWordHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.RelationshipTypeHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.SectionDividerHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.SectionErrorHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.SectionLoadingHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordAudioHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordDefinitionHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordEtymologyHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordExampleHolder

class DictionaryListAdapter(
    diffCallback: DiffUtil.ItemCallback<DictionaryItemModel>,
    private val context: Context,
    private val onAudioPlayClickListener: (url: String) -> Unit,
    private val onWordClickListener: (word: String) -> Unit,
    private val onXrefClickListener: (word: String) -> Unit,
) : ListAdapter<DictionaryItemModel, ViewHolder>(diffCallback), HeaderItemDecoration.StickyHeaderInterface {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        R.layout.item_section_divider -> SectionDividerHolder(
            binding = ItemSectionDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_attribution_text -> AttributionTextHolder(
            binding = ItemAttributionTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_word_definition -> WordDefinitionHolder(
            binding = ItemWordDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onXrefClickListener = onXrefClickListener,
        )
        R.layout.item_word_etymology -> WordEtymologyHolder(
            binding = ItemWordEtymologyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_word_example -> WordExampleHolder(
            binding = ItemWordExampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_word_related -> RelatedWordHolder(
            binding = ItemWordRelatedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener = onWordClickListener,
        )
        R.layout.item_relationship_type_text -> RelationshipTypeHolder(
            binding = ItemRelationshipTypeTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_word_audio -> WordAudioHolder(
            binding = ItemWordAudioBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onAudioPlayClickListener = onAudioPlayClickListener,
        )
        R.layout.item_section_error -> SectionErrorHolder(
            binding = ItemSectionErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_section_loading -> SectionLoadingHolder(
            binding = ItemSectionLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else -> throw RuntimeException("Unknown view holder")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SectionDividerHolder -> {
                holder.bindItem(getItem(position) as DictionarySectionDividerModel)
            }
            is AttributionTextHolder -> {
                holder.bindItem(getItem(position) as DictionaryAttributionTextModel, context)
            }
            is WordDefinitionHolder -> {
                holder.bindItem(getItem(position) as DictionaryWordDefinitionModel, context)
            }
            is WordExampleHolder -> {
                holder.bindItem(getItem(position) as DictionaryWordExampleModel, context)
            }
            is RelatedWordHolder -> {
                holder.bindItem(getItem(position) as DictionaryRelatedWordModel)
            }
            is RelationshipTypeHolder -> {
                holder.bindItem(getItem(position) as DictionaryRelationshipTypeModel)
            }
            is WordAudioHolder -> {
                holder.bindItem(getItem(position) as DictionaryWordAudioModel, context)
            }
            is SectionErrorHolder -> {
                holder.bindItem(getItem(position) as DictionarySectionErrorModel)
            }
            is SectionLoadingHolder -> {
                holder.bindItem(getItem(position) as DictionarySectionLoadingModel)
            }
            is WordEtymologyHolder -> {
                holder.bindItem(getItem(position) as DictionaryWordEtymologyModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is DictionarySectionDividerModel -> R.layout.item_section_divider
        is DictionaryAttributionTextModel -> R.layout.item_attribution_text
        is DictionaryWordDefinitionModel -> R.layout.item_word_definition
        is DictionaryRelatedWordModel -> R.layout.item_word_related
        is DictionaryRelationshipTypeModel -> R.layout.item_relationship_type_text
        is DictionaryWordAudioModel -> R.layout.item_word_audio
        is DictionaryWordEtymologyModel -> R.layout.item_word_etymology
        is DictionaryWordExampleModel -> R.layout.item_word_example
        is DictionarySectionErrorModel -> R.layout.item_section_error
        is DictionarySectionLoadingModel -> R.layout.item_section_loading
    }

    override fun getHeaderPositionForItem(pos: Int): Int {
        if (isHeader(pos)) return pos

        return currentList.subList(0, pos + 1)
            .indexOfLast { it is DictionarySectionDividerModel }
    }

    override fun getHeaderLayout(pos: Int) = R.layout.item_section_divider

    override fun bindHeaderData(header: View, pos: Int) {
        try {
            (getItem(pos) as? DictionarySectionDividerModel)?.also {
                SectionDividerHolder(ItemSectionDividerBinding.bind(header)).bindItem(it)
            }
        } catch (e: NullPointerException) { // bind() throws NPE when wrong view is passed
            // noop
        }
    }

    override fun isHeader(pos: Int) = getItem(pos) is DictionarySectionDividerModel
}