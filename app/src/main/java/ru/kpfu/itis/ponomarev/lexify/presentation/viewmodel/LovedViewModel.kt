package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.LovedWordsSorting
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.DeleteLovedUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.GetAllLovedUseCase
import javax.inject.Inject

@HiltViewModel
class LovedViewModel @Inject constructor(
    private val getAllLovedUseCase: GetAllLovedUseCase,
    private val deleteLovedUseCase: DeleteLovedUseCase,
) : ViewModel() {

    private val _lovedState = MutableStateFlow<List<LovedWordModel>>(emptyList())
    val lovedState get() = _lovedState.asStateFlow()

    var currentSorting = LovedWordsSorting.ALPHABETICALLY

    fun updateLoved() {
        viewModelScope.launch {
            _lovedState.value = getAllLovedUseCase(currentSorting)
        }
    }

    fun unlove(word: String) {
        viewModelScope.launch {
            deleteLovedUseCase(word)
            updateLoved()
        }
    }
}