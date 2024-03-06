package ru.kpfu.itis.ponomarev.lexify.domain.usecase

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import javax.inject.Inject

class GetWordAudioUseCase @Inject constructor(
    private val repository: WordRepository,
) {

    suspend operator fun invoke(word: String): List<WordAudioModel> {
        return repository.getAudio(word)
    }
}
