package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class AddDefinitionUseCase @Inject constructor(
    private val listsService: ListsService
) {
    suspend operator fun invoke(def: WordDefinitionModel, listName: String, word: String) {
        listsService.addDefinition(def, listName, word)
    }
}