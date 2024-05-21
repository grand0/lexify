package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import android.util.Log
import androidx.core.text.HtmlCompat
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordEtymologiesModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: List<String>): WordEtymologiesModel {
        if (data.isEmpty()) {
            return WordEtymologiesModel(null)
        }
        val cleanText = HtmlCompat.fromHtml(data.first(), HtmlCompat.FROM_HTML_MODE_COMPACT)
            .trim()
            .trimStart('[')
            .trimEnd(']')
            .toString()
        Log.d(this::class.simpleName, cleanText)
        return WordEtymologiesModel(text = cleanText)
    }
}