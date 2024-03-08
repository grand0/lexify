package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordRelatedBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel

class RelatedWordHolder(
    private val binding: ItemWordRelatedBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var word: String = ""

    fun bindItem(item: DictionaryRelatedWordModel) {
        word = item.word

        binding.tvText.text = item.word
    }
}