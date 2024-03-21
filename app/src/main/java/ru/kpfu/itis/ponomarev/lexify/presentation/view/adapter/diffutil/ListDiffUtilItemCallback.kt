package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordDefinitionItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordItemModel

class ListDiffUtilItemCallback : DiffUtil.ItemCallback<ListItemModel>() {
    override fun areItemsTheSame(
        oldItem: ListItemModel,
        newItem: ListItemModel,
    ) = when (oldItem) {
        is ListWordItemModel -> oldItem.word == (newItem as? ListWordItemModel)?.word
        is ListWordDefinitionItemModel -> oldItem.id == (newItem as? ListWordDefinitionItemModel)?.id
    }

    override fun areContentsTheSame(
        oldItem: ListItemModel,
        newItem: ListItemModel,
    ) = oldItem == newItem
}