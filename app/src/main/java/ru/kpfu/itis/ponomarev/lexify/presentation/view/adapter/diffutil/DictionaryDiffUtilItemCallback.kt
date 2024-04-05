package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryItemModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelatedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryRelationshipTypeModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionDividerModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionErrorModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySectionLoadingModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordAudioModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordEtymologyModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel

class DictionaryDiffUtilItemCallback : DiffUtil.ItemCallback<DictionaryItemModel>() {
    override fun areItemsTheSame(
        oldItem: DictionaryItemModel,
        newItem: DictionaryItemModel,
    ) = when (oldItem) {
        is DictionaryAttributionTextModel -> oldItem.text == (newItem as? DictionaryAttributionTextModel)?.text
        is DictionaryRelatedWordModel -> oldItem.word == (newItem as? DictionaryRelatedWordModel)?.word
        is DictionaryRelationshipTypeModel -> oldItem.type == (newItem as? DictionaryRelationshipTypeModel)?.type
        is DictionarySectionDividerModel -> oldItem.name == (newItem as? DictionarySectionDividerModel)?.name
        is DictionarySectionErrorModel -> oldItem.section == (newItem as? DictionarySectionErrorModel)?.section
        is DictionarySectionLoadingModel -> oldItem.section == (newItem as? DictionarySectionLoadingModel)?.section
        is DictionaryWordAudioModel -> oldItem.id == (newItem as? DictionaryWordAudioModel)?.id
        is DictionaryWordDefinitionModel -> oldItem.id == (newItem as? DictionaryWordDefinitionModel)?.id
        is DictionaryWordEtymologyModel -> oldItem.text == (newItem as? DictionaryWordEtymologyModel)?.text
        is DictionaryWordExampleModel -> oldItem.id == (newItem as? DictionaryWordExampleModel)?.id
    }

    override fun areContentsTheSame(
        oldItem: DictionaryItemModel,
        newItem: DictionaryItemModel,
    ) = oldItem == newItem
}