package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel

sealed class DictionaryItemModel

data class DictionarySectionDividerModel(val name: String) : DictionaryItemModel()

data class DictionaryAttributionTextModel(val text: String, val url: String) : DictionaryItemModel()

data class DictionaryWordDefinitionModel(
    val partOfSpeech: String,
    val text: String,
    val labels: List<WordDefinitionLabelModel>,
) : DictionaryItemModel()
