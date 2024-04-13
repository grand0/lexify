package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel

sealed class WordOfTheDayUiModel {
    class Ok(val word: WordOfTheDayModel) : WordOfTheDayUiModel()
    data object Loading : WordOfTheDayUiModel()
    data object Error : WordOfTheDayUiModel()
}