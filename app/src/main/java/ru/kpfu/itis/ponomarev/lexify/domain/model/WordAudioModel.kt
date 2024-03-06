package ru.kpfu.itis.ponomarev.lexify.domain.model

data class WordAudioModel(
    val duration: Double,
    val attributionText: String,
    val attributionUrl: String,
    val fileUrl: String,
)
