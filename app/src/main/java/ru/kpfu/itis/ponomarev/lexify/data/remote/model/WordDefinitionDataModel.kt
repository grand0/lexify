package ru.kpfu.itis.ponomarev.lexify.data.remote.model

data class WordDefinitionDataModel(
    val id: String?,
    val partOfSpeech: String?,
    val attributionText: String,
    val attributionUrl: String,
    val text: String?,
    val labels: List<WordDefinitionLabelDataModel>,
    val word: String,
)

data class WordDefinitionLabelDataModel(
    val text: String,
    val type: String,
)
