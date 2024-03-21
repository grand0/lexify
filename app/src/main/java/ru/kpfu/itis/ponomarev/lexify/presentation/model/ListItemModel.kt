package ru.kpfu.itis.ponomarev.lexify.presentation.model

sealed class ListItemModel

data class ListWordItemModel(val word: String) : ListItemModel()

data class ListWordDefinitionItemModel(
    val id: String,
    val partOfSpeech: String?,
    val text: String,
    val labels: String?,
) : ListItemModel()