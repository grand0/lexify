package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper

import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionWithLabelsEntity
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionLabelModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListDefinitionEntityMapper @Inject constructor() {

    fun mapEntityToModel(entity: ListDefinitionWithLabelsEntity, listName: String) = ListDefinitionModel(
        id = entity.listDefinition.id,
        listName = listName,
        word = entity.listDefinition.word,
        text = entity.listDefinition.text,
        partOfSpeech = entity.listDefinition.partOfSpeech,
        labels = entity.labels.map {
            ListDefinitionLabelModel(
                type = it.type,
                text = it.text,
            )
        }
    )
}