package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordRelatedBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel

class RelatedWordHolder(
    private val binding: ItemWordRelatedBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryRelatedWordModel) {
        binding.tvText.text = item.word
    }
}