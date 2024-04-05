package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordAudioDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordAudioModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordAudioDataModel) = WordAudioModel(
        id = data.id,
        duration = data.duration,
        attributionText = data.attributionText,
        attributionUrl = data.attributionUrl,
        fileUrl = data.fileUrl,
    )
}