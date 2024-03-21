package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.CreateListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.DeleteListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetAllListsUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.ListsSorting
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getAllListsUseCase: GetAllListsUseCase,
    private val createListUseCase: CreateListUseCase,
    private val deleteListUseCase: DeleteListUseCase,
) : ViewModel() {

    private val _listsState = MutableStateFlow<List<ListModel>>(emptyList())
    val listsState get() = _listsState.asStateFlow()

    init {
        updateLists()
    }

    fun updateLists(sorting: ListsSorting = ListsSorting.RECENT) {
        viewModelScope.launch {
            _listsState.value = getAllListsUseCase(sorting)
        }
    }

    fun createList(name: String) {
        viewModelScope.launch {
            createListUseCase(name)
            updateLists()
        }
    }

    fun deleteList(name: String) {
        viewModelScope.launch {
            deleteListUseCase(name)
            updateLists()
        }
    }
}