package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionErrorBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionErrorModel

class SectionErrorHolder(
    private val binding: ItemSectionErrorBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionarySectionErrorModel) {
        binding.tvText.text = item.message
    }
}