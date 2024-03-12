package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemLovedWordBinding
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel

class LovedWordHolder(
    private val binding: ItemLovedWordBinding,
    private val onItemClicked: (LovedWordModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: LovedWordModel) {
        binding.tvWord.text = item.word
        binding.root.setOnClickListener { onItemClicked(item) }
    }
}