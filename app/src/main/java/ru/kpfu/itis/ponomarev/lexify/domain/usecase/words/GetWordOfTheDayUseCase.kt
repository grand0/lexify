package ru.kpfu.itis.ponomarev.lexify.domain.usecase.words

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordOfTheDayUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend operator fun invoke(): WordOfTheDayModel {
        return repository.getWordOfTheDay()
    }
}
