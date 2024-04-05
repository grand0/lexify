package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.ListsSorting
import javax.inject.Inject

class GetAllListsUseCase @Inject constructor(
    private val listsRepository: ListsRepository,
) {
    suspend operator fun invoke(sorting: ListsSorting = ListsSorting.RECENT): List<ListModel> {
        return listsRepository.getAll(sorting)
    }
}