package ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved

import ru.kpfu.itis.ponomarev.lexify.domain.service.LovedService
import javax.inject.Inject

class DeleteLovedUseCase @Inject constructor(
    private val lovedService: LovedService,
) {
    suspend operator fun invoke(word: String) {
        lovedService.deleteWord(word)
    }
}