package ru.kpfu.itis.ponomarev.lexify.presentation.model

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel

sealed class RandomWordsUiModel {
    class Ok(val words: List<RandomWordModel>) : RandomWordsUiModel()
    data object Loading : RandomWordsUiModel()
    data object Error : RandomWordsUiModel()
}