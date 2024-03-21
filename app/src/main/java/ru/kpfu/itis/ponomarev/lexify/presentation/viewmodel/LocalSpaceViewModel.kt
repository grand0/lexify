package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetRecentListsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.GetRecentLovedUseCase
import javax.inject.Inject

@HiltViewModel
class LocalSpaceViewModel @Inject constructor(
    private val getRecentLovedUseCase: GetRecentLovedUseCase,
    private val getRecentListsUseCase: GetRecentListsUseCase,
) : ViewModel() {

    private val _recentLovedState = MutableStateFlow<List<String>>(emptyList())
    val recentLovedState get() = _recentLovedState.asStateFlow()

    private val _recentListsState = MutableStateFlow<List<String>>(emptyList())
    val recentListsState get() = _recentListsState.asStateFlow()

    init {
        updateLoved()
        updateLists()
    }

    fun updateLoved() {
        viewModelScope.launch {
            _recentLovedState.value = getRecentLovedUseCase().map { it.word }
        }
    }

    fun updateLists() {
        viewModelScope.launch {
            _recentListsState.value = getRecentListsUseCase().map { it.name }
        }
    }
}