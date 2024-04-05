package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordOfTheDayDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordRelatedWordsDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRelatedWordsModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordRelatedWordsDataModel) = RelatedWordsModel(
        relationshipType = data.relationshipType,
        words = data.words,
    )
}