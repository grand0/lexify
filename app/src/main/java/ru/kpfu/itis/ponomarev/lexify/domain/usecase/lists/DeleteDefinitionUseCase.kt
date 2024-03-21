package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class DeleteDefinitionUseCase @Inject constructor(
    private val listsService: ListsService
) {
    suspend operator fun invoke(id: String, listName: String) {
        listsService.deleteDefinition(id, listName)
    }
}