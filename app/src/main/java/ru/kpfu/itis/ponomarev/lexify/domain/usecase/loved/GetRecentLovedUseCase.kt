package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import javax.inject.Inject

class GetRecentLovedUseCase @Inject constructor(
    private val service: LovedService,
) {
    suspend operator fun invoke(limit: Int = 5): List<LovedWordModel> {
        return service.getRecent(limit)
    }
}