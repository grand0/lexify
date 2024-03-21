package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListSelectorBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ListSelectorHolder

class ListsSelectorListAdapter(
    diffCallback: ItemCallback<ListSelectorModel>,
    private val onItemClicked: (ListSelectorModel) -> Unit,
) : ListAdapter<ListSelectorModel, ListSelectorHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListSelectorHolder(
        ItemListSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClicked = onItemClicked,
    )

    override fun onBindViewHolder(holder: ListSelectorHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}