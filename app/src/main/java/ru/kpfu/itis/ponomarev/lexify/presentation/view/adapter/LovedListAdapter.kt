package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemLovedWordBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.view.holder.LovedWordHolder

class LovedListAdapter(
    diffCallback: ItemCallback<LovedWordModel>,
    private val onItemClicked: (LovedWordModel) -> Unit,
) : ListAdapter<LovedWordModel, LovedWordHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LovedWordHolder =
        LovedWordHolder(
            ItemLovedWordBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked = onItemClicked,
        )

    override fun onBindViewHolder(holder: LovedWordHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}