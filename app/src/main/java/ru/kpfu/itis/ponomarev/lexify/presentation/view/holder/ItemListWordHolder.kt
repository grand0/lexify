package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemListWordBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListWordItemModel

class ItemListWordHolder(
    private val binding: ItemListWordBinding,
    private val wordClicked: (ListWordItemModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ListWordItemModel) {
        binding.tvWord.text = item.word
        binding.root.setOnClickListener { wordClicked(item) }
    }
}