package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel

class ListsSelectorDiffUtilItemCallback : DiffUtil.ItemCallback<ListSelectorModel>() {
    override fun areItemsTheSame(
        oldItem: ListSelectorModel,
        newItem: ListSelectorModel,
    ) = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ListSelectorModel,
        newItem: ListSelectorModel,
    ) = oldItem == newItem
}