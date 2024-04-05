package ru.kpfu.itis.ponomarev.lexify.data.remote.model

data class WordRelatedWordsDataModel(
    val relationshipType: String,
    val words: List<String>,
)