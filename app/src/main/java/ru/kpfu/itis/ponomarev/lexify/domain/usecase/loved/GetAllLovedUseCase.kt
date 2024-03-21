package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.LovedWordsSorting
import javax.inject.Inject

class GetAllLovedUseCase @Inject constructor(
    private val lovedService: LovedService,
) {
    suspend operator fun invoke(sorting: LovedWordsSorting = LovedWordsSorting.ALPHABETICALLY): List<LovedWordModel> {
        return lovedService.getAll(sorting)
    }
}