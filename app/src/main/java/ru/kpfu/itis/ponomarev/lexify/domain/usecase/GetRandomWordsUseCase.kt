package ru.kpfu.itis.ponomarev.lexify.domain.usecase

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Inject

class GetRandomWordsUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend operator fun invoke(limit: Int = 10): List<RandomWordModel> {
        return repository.getRandomWords(limit)
    }
}