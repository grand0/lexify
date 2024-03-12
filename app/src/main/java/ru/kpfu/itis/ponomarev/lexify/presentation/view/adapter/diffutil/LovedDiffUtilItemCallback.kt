package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel

class LovedDiffUtilItemCallback : DiffUtil.ItemCallback<LovedWordModel>() {
    override fun areItemsTheSame(
        oldItem: LovedWordModel,
        newItem: LovedWordModel,
    ) = oldItem.word == newItem.word

    override fun areContentsTheSame(
        oldItem: LovedWordModel,
        newItem: LovedWordModel,
    ) = oldItem == newItem
}