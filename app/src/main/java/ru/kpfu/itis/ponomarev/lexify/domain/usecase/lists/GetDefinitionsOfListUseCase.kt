package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import javax.inject.Inject

class GetDefinitionsOfListUseCase @Inject constructor(
    private val listsRepository: ListsRepository,
) {
    suspend operator fun invoke(name: String): List<ListDefinitionModel> {
        return listsRepository.getDefinitionsOfList(name)
    }
}