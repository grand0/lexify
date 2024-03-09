package ru.kpfu.itis.ponomarev.lexify.domain.model

data class WordExampleModel(
    val year: Int?,
    val url: String?,
    val text: String,
    val title: String,
    val author: String?,
    val word: String?,
)
