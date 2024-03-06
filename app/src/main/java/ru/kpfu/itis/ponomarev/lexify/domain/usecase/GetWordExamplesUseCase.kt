package ru.kpfu.itis.ponomarev.lexify.domain.usecase

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import javax.inject.Inject

class GetWordExamplesUseCase @Inject constructor(
    private val repository: WordRepository,
) {

    suspend operator fun invoke(word: String): List<WordExampleModel> {
        return repository.getExamples(word)
    }
}
