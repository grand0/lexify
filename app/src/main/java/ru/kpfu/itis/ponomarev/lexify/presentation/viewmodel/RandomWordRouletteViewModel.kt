package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetRandomWordsUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.model.RandomWordsUiModel
import javax.inject.Inject

@HiltViewModel
class RandomWordRouletteViewModel @Inject constructor(
    private val getRandomWordsUseCase: GetRandomWordsUseCase,
) : ViewModel() {

    private val _randomWordsState = MutableStateFlow<RandomWordsUiModel>(RandomWordsUiModel.Loading)
    val randomWordsState get() = _randomWordsState.asStateFlow()

    fun updateRandomWords() {
        viewModelScope.launch {
            try {
                _randomWordsState.value = RandomWordsUiModel.Ok(getRandomWordsUseCase(limit = 30))
            } catch (e: Exception) {
                _randomWordsState.value = RandomWordsUiModel.Error
            }
        }
    }
}