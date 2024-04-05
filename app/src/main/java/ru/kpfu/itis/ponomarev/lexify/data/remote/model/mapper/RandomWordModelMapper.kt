package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.RandomWordDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomWordModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: RandomWordDataModel) = RandomWordModel(data.word)
}