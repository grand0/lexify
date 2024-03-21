package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class CheckDefinitionInListUseCase @Inject constructor(
    private val listsService: ListsService,
) {

    suspend operator fun invoke(id: String, listName: String): Boolean {
        return listsService.checkDefinition(id, listName)
    }
}