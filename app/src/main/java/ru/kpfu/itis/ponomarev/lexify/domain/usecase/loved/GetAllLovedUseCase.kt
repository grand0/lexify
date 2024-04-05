package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.LovedWordsSorting
import javax.inject.Inject

class GetAllLovedUseCase @Inject constructor(
    private val lovedRepository: LovedRepository,
) {
    suspend operator fun invoke(sorting: LovedWordsSorting = LovedWordsSorting.ALPHABETICALLY): List<LovedWordModel> {
        return lovedRepository.getAll(sorting)
    }
}