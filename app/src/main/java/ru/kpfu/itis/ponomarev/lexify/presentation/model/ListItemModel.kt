package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionLabelModel

sealed class ListItemModel

data class ListWordItemModel(val word: String) : ListItemModel()

data class ListWordDefinitionItemModel(
    val id: String,
    val partOfSpeech: String?,
    val text: String,
    val labels: List<ListDefinitionLabelModel>,
) : ListItemModel()