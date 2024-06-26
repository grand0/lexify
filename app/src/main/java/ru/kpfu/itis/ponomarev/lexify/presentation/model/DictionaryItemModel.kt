package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel

sealed class DictionaryItemModel

data class DictionarySectionErrorModel(
    val message: String,
    val section: DictionarySection,
) : DictionaryItemModel()

data class DictionarySectionLoadingModel(
    val section: DictionarySection,
) : DictionaryItemModel()

data class DictionarySectionDividerModel(val name: String) : DictionaryItemModel()

data class DictionaryAttributionTextModel(val text: String, val url: String) : DictionaryItemModel()

data class DictionaryRelationshipTypeModel(val type: String) : DictionaryItemModel()

data class DictionaryWordDefinitionModel(
    val id: String,
    val partOfSpeech: String?,
    val text: String,
    val labels: List<WordDefinitionLabelModel>,
    val xrefs: Set<String>,
) : DictionaryItemModel()

data class DictionaryWordEtymologyModel(
    val text: String,
) : DictionaryItemModel()

data class DictionaryRelatedWordModel(
    val word: String,
) : DictionaryItemModel()

data class DictionaryWordExampleModel(
    val id: Int,
    val text: String,
    val url: String?,
    val title: String?,
    val author: String?,
    val year: Int?,
    val word: String?,
) : DictionaryItemModel()

data class DictionaryWordAudioModel(
    val id: Int,
    val duration: Double,
    val fileUrl: String,
    val attributionText: String,
    val attributionUrl: String,
) : DictionaryItemModel()
