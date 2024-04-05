package ru.kpfu.itis.ponomarev.lexify.domain.model

data class ListDefinitionModel(
    val id: String,
    val listName: String,
    val word: String,
    val text: String,
    val partOfSpeech: String?,
    val labels: List<ListDefinitionLabelModel>,
)
