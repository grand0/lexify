package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel

sealed class DictionaryItemModel

data class DictionarySectionErrorModel(val message: String) : DictionaryItemModel()

data object DictionarySectionLoadingModel : DictionaryItemModel()

data class DictionarySectionDividerModel(val name: String) : DictionaryItemModel()

data class DictionaryAttributionTextModel(val text: String, val url: String) : DictionaryItemModel()

data class DictionaryRelationshipTypeModel(val type: String) : DictionaryItemModel()

data class DictionaryWordDefinitionModel(
    val partOfSpeech: String?,
    val text: String,
    val labels: List<WordDefinitionLabelModel>,
) : DictionaryItemModel()

data class DictionaryWordEtymologyModel(
    val text: String,
) : DictionaryItemModel()

data class DictionaryRelatedWordModel(
    val word: String,
) : DictionaryItemModel()

data class DictionaryWordExampleModel(
    val text: String,
    val url: String?,
    val title: String?,
    val author: String?,
    val year: Int?
) : DictionaryItemModel()

data class DictionaryWordAudioModel(
    val duration: Double,
    val fileUrl: String,
    val attributionText: String,
    val attributionUrl: String,
) : DictionaryItemModel()
