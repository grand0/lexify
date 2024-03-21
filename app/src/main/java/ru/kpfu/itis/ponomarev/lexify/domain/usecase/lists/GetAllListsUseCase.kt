package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.ListsSorting
import javax.inject.Inject

class GetAllListsUseCase @Inject constructor(
    private val service: ListsService,
) {
    suspend operator fun invoke(sorting: ListsSorting = ListsSorting.RECENT): List<ListModel> {
        return service.getAll(sorting)
    }
}