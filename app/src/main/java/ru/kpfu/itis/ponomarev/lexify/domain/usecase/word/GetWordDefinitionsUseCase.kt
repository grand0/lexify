package ru.kpfu.itis.ponomarev.lexify.domain.usecase.word

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import javax.inject.Inject

class GetWordDefinitionsUseCase @Inject constructor(
    private val repository: WordRepository,
) {

    suspend operator fun invoke(word: String): List<WordDefinitionModel> {
        return repository.getDefinitions(word)
    }
}