package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetRandomWordsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetWordOfTheDayUseCase
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getRandomWordsUseCase: GetRandomWordsUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
) : ViewModel() {

    private val _randomWordsState = MutableStateFlow<List<RandomWordModel>>(listOf())
    val randomWordsState get() = _randomWordsState.asStateFlow()

    private val _wordOfTheDayState = MutableStateFlow(WordOfTheDayModel("", Calendar.getInstance().time))
    val wordOfTheDayState get() = _wordOfTheDayState.asStateFlow()

    init {
        updateRandomWords()
        updateWordOfTheDay()
    }

    fun updateRandomWords() {
        viewModelScope.launch {
            _randomWordsState.value = getRandomWordsUseCase()
        }
    }

    fun updateWordOfTheDay() {
        viewModelScope.launch {
            _wordOfTheDayState.value = getWordOfTheDayUseCase()
        }
    }
}