package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper

import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListEntityMapper @Inject constructor() {

    fun mapEntityToModel(entity: ListEntity) = ListModel(
        name = entity.name,
    )
}