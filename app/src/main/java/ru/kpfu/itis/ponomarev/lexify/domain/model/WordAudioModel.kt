package ru.kpfu.itis.ponomarev.lexify.domain.model

data class WordAudioModel(
    val id: Int,
    val duration: Double,
    val attributionText: String,
    val attributionUrl: String,
    val fileUrl: String,
)
