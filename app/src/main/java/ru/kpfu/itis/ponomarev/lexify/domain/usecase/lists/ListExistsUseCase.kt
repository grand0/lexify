package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import javax.inject.Inject

class ListExistsUseCase @Inject constructor(
    private val listsRepository: ListsRepository,
) {
    suspend operator fun invoke(name: String): Boolean {
        return listsRepository.listExists(name)
    }
}