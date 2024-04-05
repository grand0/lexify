package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import javax.inject.Inject

class DeleteDefinitionUseCase @Inject constructor(
    private val listsRepository: ListsRepository,
) {
    suspend operator fun invoke(id: String, listName: String) {
        listsRepository.deleteDefinition(id, listName)
    }
}