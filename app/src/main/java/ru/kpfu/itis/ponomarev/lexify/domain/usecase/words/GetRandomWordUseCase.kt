package ru.kpfu.itis.ponomarev.lexify.domain.usecase.words

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Inject

class GetRandomWordUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend operator fun invoke(): RandomWordModel {
        return repository.getRandomWord()
    }
}