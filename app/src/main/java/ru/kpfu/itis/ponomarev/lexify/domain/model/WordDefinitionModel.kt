package ru.kpfu.itis.ponomarev.lexify.domain.model

data class WordDefinitionModel(
    val id: String,
    val partOfSpeech: String?,
    val attributionText: String,
    val attributionUrl: String,
    val text: String,
    val labels: List<WordDefinitionLabelModel>,
)