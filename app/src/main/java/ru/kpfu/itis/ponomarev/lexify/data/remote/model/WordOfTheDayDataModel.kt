package ru.kpfu.itis.ponomarev.lexify.data.remote.model

import com.google.gson.annotations.SerializedName

data class WordOfTheDayDataModel(
    @SerializedName("_id") val id: String,
    @SerializedName("pdd") val date: String,
    val word: String,
    val note: String?,
)