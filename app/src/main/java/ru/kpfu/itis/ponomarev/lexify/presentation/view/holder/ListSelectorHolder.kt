package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListSelectorBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel

class ListSelectorHolder(
    private val binding: ItemListSelectorBinding,
    private val onItemClicked: (ListSelectorModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ListSelectorModel) {
        binding.tvName.text = item.name
        binding.checkbox.isChecked = item.isChecked
        binding.root.setOnClickListener {
            onItemClicked(item)
        }
        binding.checkbox.setOnClickListener {
            onItemClicked(item)
        }
    }
}