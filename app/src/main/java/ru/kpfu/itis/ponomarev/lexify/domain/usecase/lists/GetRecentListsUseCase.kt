package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class GetRecentListsUseCase @Inject constructor(
    private val service: ListsService,
) {
    suspend operator fun invoke(limit: Int = 5): List<ListModel> {
        return service.getRecent(limit)
    }
}