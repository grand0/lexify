package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import javax.inject.Inject

class GetRecentLovedUseCase @Inject constructor(
    private val lovedRepository: LovedRepository,
) {
    suspend operator fun invoke(limit: Int = 5): List<LovedWordModel> {
        return lovedRepository.getRecent(limit)
    }
}