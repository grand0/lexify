package ru.kpfu.itis.ponomarev.lexify.domain.model

import com.google.gson.annotations.SerializedName

data class WordOfTheDayModel(
    val word: String,
    @SerializedName("pdd") val dateStr: String
)