package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemRelationshipTypeTextBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelationshipTypeModel

class RelationshipTypeHolder(
    private val binding: ItemRelationshipTypeTextBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryRelationshipTypeModel) {
        binding.tvText.text = item.type
    }
}