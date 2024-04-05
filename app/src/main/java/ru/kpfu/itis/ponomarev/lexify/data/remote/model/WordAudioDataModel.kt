package ru.kpfu.itis.ponomarev.lexify.data.remote.model

data class WordAudioDataModel(
    val id: Int,
    val duration: Double,
    val attributionText: String,
    val attributionUrl: String,
    val fileUrl: String,
    val word: String,
)