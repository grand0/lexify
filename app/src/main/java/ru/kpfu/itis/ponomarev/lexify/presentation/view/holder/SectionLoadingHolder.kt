package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemSectionLoadingBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionLoadingModel

class SectionLoadingHolder(
    binding: ItemSectionLoadingBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionarySectionLoadingModel) {
        // noop for now
    }
}
