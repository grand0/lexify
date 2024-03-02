package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionDividerBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel

class SectionDividerHolder(
    private val binding: ItemSectionDividerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionarySectionDividerModel) {
        binding.tvName.text = item.name
    }
}
