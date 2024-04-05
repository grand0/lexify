package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import javax.inject.Inject

class AddLovedUseCase @Inject constructor(
    private val lovedRepository: LovedRepository,
) {
    suspend operator fun invoke(word: String) {
        lovedRepository.addWord(word)
    }
}