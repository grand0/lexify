package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemAttributionTextBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionDividerBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordDefinitionBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.AttributionTextHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.SectionDividerHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.WordDefinitionHolder
import java.lang.RuntimeException

class DictionaryListAdapter(
    private val items: List<DictionaryItemModel>,
    private val context: Context,
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        R.layout.item_section_divider -> SectionDividerHolder(
            binding = ItemSectionDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_attribution_text -> AttributionTextHolder(
            binding = ItemAttributionTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        R.layout.item_word_definition -> WordDefinitionHolder(
            binding = ItemWordDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else -> throw RuntimeException("Unknown view holder")
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SectionDividerHolder -> {
                holder.bindItem(items[position] as DictionarySectionDividerModel)
            }
            is AttributionTextHolder -> {
                holder.bindItem(items[position] as DictionaryAttributionTextModel, context)
            }
            is WordDefinitionHolder -> {
                holder.bindItem(items[position] as DictionaryWordDefinitionModel, context)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is DictionarySectionDividerModel -> R.layout.item_section_divider
        is DictionaryAttributionTextModel -> R.layout.item_attribution_text
        is DictionaryWordDefinitionModel -> R.layout.item_word_definition
    }
}