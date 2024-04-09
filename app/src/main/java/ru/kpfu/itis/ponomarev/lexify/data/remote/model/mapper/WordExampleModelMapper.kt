package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExampleDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.util.ResManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordExampleModelMapper @Inject constructor(
    private val resManager: ResManager,
) {

    fun mapDataModelToModel(data: WordExampleDataModel) = WordExampleModel(
        id = data.id,
        year = data.year,
        url = data.url,
        text = data.text,
        title = data.title ?: resManager.getString(R.string.unknown),
        author = data.author,
        word = data.word,
    )
}