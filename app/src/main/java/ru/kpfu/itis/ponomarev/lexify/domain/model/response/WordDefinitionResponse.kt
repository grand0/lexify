package ru.kpfu.itis.ponomarev.lexify.domain.model.response

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel

data class WordDefinitionResponse(
    val id: String?,
    val partOfSpeech: String?,
    val attributionText: String,
    val attributionUrl: String,
    val text: String?,
    val labels: List<WordDefinitionLabelModel>,
)
