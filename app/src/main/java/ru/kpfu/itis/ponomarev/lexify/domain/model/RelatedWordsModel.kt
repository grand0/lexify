package ru.kpfu.itis.ponomarev.lexify.domain.model

data class RelatedWordsModel(
    val relationshipType: String,
    val words: List<String>,
)
