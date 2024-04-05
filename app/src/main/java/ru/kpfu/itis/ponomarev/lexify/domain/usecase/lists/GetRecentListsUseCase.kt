package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import javax.inject.Inject

class GetRecentListsUseCase @Inject constructor(
    private val listsRepository: ListsRepository,
) {
    suspend operator fun invoke(limit: Int = 5): List<ListModel> {
        return listsRepository.getRecent(limit)
    }
}