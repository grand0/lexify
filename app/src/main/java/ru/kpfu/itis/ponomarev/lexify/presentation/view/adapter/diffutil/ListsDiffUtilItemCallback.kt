package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel

class ListsDiffUtilItemCallback : DiffUtil.ItemCallback<ListModel>() {
    override fun areItemsTheSame(
        oldItem: ListModel,
        newItem: ListModel,
    ) = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ListModel,
        newItem: ListModel,
    ) = oldItem == newItem
}