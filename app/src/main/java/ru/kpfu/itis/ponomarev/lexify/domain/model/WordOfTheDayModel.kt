package ru.kpfu.itis.ponomarev.lexify.domain.model

import java.util.Date

data class WordOfTheDayModel(
    val word: String,
    val date: Date,
)