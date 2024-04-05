package ru.kpfu.itis.ponomarev.lexify.data.remote.model

import com.google.gson.annotations.SerializedName

data class WordExamplesDataModel(
    val examples: List<WordExampleDataModel>,
)

data class WordExampleDataModel(
    @SerializedName("exampleId") val id: Int,
    val year: Int?,
    val url: String?,
    val word: String,
    val text: String,
    val title: String?,
    val author: String?,
)
