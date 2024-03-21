package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListWordBinding
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordDefinitionBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordDefinitionItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ItemListWordHolder
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ListWordDefinitionHolder

class ListListAdapter(
    diffCallback: ItemCallback<ListItemModel>,
    private val context: Context,
    private val wordClicked: (ListWordItemModel) -> Unit,
) : ListAdapter<ListItemModel, ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.item_list_word -> ItemListWordHolder(
            binding = ItemListWordBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            wordClicked = wordClicked,
        )
        R.layout.item_list_word_definition -> ListWordDefinitionHolder(
            binding = ItemWordDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
        else -> throw RuntimeException("Unknown view holder")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemListWordHolder -> holder.bindItem(getItem(position) as ListWordItemModel)
            is ListWordDefinitionHolder -> holder.bindItem(getItem(position) as ListWordDefinitionItemModel, context)
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is ListWordItemModel -> R.layout.item_list_word
        is ListWordDefinitionItemModel -> R.layout.item_list_word_definition
    }
}