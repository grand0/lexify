package ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionsModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.ListsService
import javax.inject.Inject

class GetDefinitionsOfListUseCase @Inject constructor(
    private val service: ListsService,
) {
    suspend operator fun invoke(name: String): ListDefinitionsModel {
        return service.getDefinitionsOfList(name)
    }
}