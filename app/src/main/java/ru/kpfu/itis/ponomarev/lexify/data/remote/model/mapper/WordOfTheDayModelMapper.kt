package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordOfTheDayDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordOfTheDayModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordOfTheDayDataModel): WordOfTheDayModel = WordOfTheDayModel(
        word = data.word,
        date = dateFormat.parse(data.date) ?: Calendar.getInstance().time,
    )

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }
}