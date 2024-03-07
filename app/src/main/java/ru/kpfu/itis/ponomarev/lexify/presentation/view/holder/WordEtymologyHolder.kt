package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordEtymologyBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordEtymologyModel

class WordEtymologyHolder(
    private val binding: ItemWordEtymologyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var copyableText = ""

    fun bindItem(item: DictionaryWordEtymologyModel) {
        copyableText = item.text

        binding.tvText.text = item.text
    }
}