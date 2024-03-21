package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel

class ItemListHolder(
    private val binding: ItemListBinding,
    private val onItemClicked: (ListModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ListModel) {
        binding.tvName.text = item.name
        binding.root.setOnClickListener { onItemClicked(item) }
    }
}