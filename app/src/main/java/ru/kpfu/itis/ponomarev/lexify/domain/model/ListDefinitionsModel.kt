package ru.kpfu.itis.ponomarev.lexify.domain.model

data class ListDefinitionsModel(
    val definitions: Map<String, List<WordDefinitionModel>>,
)