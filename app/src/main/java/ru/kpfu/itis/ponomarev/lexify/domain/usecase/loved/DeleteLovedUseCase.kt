package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import javax.inject.Inject

class DeleteLovedUseCase @Inject constructor(
    private val lovedRepository: LovedRepository,
) {
    suspend operator fun invoke(word: String) {
        lovedRepository.deleteWord(word)
    }
}