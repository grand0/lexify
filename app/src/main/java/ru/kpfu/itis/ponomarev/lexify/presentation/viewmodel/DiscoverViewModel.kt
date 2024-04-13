package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetRandomWordsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetWordOfTheDayUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DiscoverScreenException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DiscoverScreenRandomWordException
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DiscoverScreenWordOfTheDayException
import ru.kpfu.itis.ponomarev.lexify.presentation.model.RandomWordsUiModel
import ru.kpfu.itis.ponomarev.lexify.presentation.model.WordOfTheDayUiModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getRandomWordsUseCase: GetRandomWordsUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
) : ViewModel() {

    private val _randomWordsState = MutableStateFlow<RandomWordsUiModel>(RandomWordsUiModel.Loading)
    val randomWordsState get() = _randomWordsState.asStateFlow()

    private val _wordOfTheDayState = MutableStateFlow<WordOfTheDayUiModel>(WordOfTheDayUiModel.Loading)
    val wordOfTheDayState get() = _wordOfTheDayState.asStateFlow()

    init {
        updateRandomWords()
        updateWordOfTheDay()
    }

    fun updateRandomWords() {
        viewModelScope.launch {
            _randomWordsState.value = RandomWordsUiModel.Loading
            try {
                _randomWordsState.value = RandomWordsUiModel.Ok(getRandomWordsUseCase())
            } catch (e: Exception) {
                _randomWordsState.value = RandomWordsUiModel.Error
            }
        }
    }

    fun updateWordOfTheDay() {
        viewModelScope.launch {
            _wordOfTheDayState.value = WordOfTheDayUiModel.Loading
            try {
                _wordOfTheDayState.value = WordOfTheDayUiModel.Ok(getWordOfTheDayUseCase())
            } catch (e: Exception) {
                _wordOfTheDayState.value = WordOfTheDayUiModel.Error
            }
        }
    }
}