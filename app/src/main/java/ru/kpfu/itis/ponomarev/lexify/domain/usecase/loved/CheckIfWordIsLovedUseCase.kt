package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import javax.inject.Inject

class CheckIfWordIsLovedUseCase @Inject constructor(
    private val lovedRepository: LovedRepository,
) {
    suspend operator fun invoke(word: String): Boolean {
        return lovedRepository.isLoved(word)
    }
}