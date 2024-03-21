package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import javax.inject.Inject

class AddLovedUseCase @Inject constructor(
    private val lovedService: LovedService,
) {
    suspend operator fun invoke(word: String) {
        lovedService.addWord(word)
    }
}