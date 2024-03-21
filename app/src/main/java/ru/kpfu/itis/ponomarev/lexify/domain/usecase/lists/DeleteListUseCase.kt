package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val listsService: ListsService
) {
    suspend operator fun invoke(name: String) {
        listsService.deleteList(name)
    }
}