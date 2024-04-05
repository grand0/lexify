package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExampleDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordExampleModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordExampleDataModel) = WordExampleModel(
        id = data.id,
        year = data.year,
        url = data.url,
        text = data.text,
        title = data.title ?: "Unknown", // TODO: replace with string resource
        author = data.author,
        word = data.word,
    )
}