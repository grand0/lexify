package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.words.GetRandomWordsUseCase
import javax.inject.Inject

@HiltViewModel
class RandomWordRouletteViewModel @Inject constructor(
    private val getRandomWordsUseCase: GetRandomWordsUseCase,
) : ViewModel() {

    private val _randomWordsState = MutableStateFlow<List<RandomWordModel>>(listOf())
    val randomWordsState get() = _randomWordsState.asStateFlow()

    fun updateRandomWords() {
        viewModelScope.launch {
            _randomWordsState.value = getRandomWordsUseCase(limit = 30)
        }
    }
}