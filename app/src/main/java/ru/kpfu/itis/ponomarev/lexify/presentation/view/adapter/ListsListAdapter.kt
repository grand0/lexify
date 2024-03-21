package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.ItemListHolder

class ListsListAdapter(
    diffCallback: ItemCallback<ListModel>,
    private val onItemClicked: (ListModel) -> Unit,
) : ListAdapter<ListModel, ItemListHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemListHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked,
        )

    override fun onBindViewHolder(holder: ItemListHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}