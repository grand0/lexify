package ru.kpfu.itis.ponomarev.lexify.domain.usecase;

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import javax.inject.Inject;

import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository;

class GetWordOfTheDayUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend operator fun invoke(): WordOfTheDayModel {
        return repository.getWordOfTheDay()
    }
}
