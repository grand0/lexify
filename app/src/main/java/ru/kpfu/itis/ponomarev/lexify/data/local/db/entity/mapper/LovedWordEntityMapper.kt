package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper

import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LovedWordEntityMapper @Inject constructor() {

    fun mapEntityToModel(entity: LovedWordEntity) = LovedWordModel(
        word = entity.word,
    )
}
