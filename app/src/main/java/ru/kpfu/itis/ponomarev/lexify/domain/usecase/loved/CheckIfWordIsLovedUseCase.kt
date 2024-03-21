package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import javax.inject.Inject

class CheckIfWordIsLovedUseCase @Inject constructor(
    private val lovedService: LovedService,
) {
    suspend operator fun invoke(word: String): Boolean {
        return lovedService.isLoved(word)
    }
}