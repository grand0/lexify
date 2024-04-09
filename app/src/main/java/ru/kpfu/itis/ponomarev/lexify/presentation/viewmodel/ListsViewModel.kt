package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.CreateListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.DeleteListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetAllListsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.ListsSorting
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.ListExistsUseCase
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getAllListsUseCase: GetAllListsUseCase,
    private val createListUseCase: CreateListUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val listExistsUseCase: ListExistsUseCase,
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

    fun createList(name: String): CompletableFuture<Boolean> {
        return viewModelScope.async {
            if (listExistsUseCase(name)) return@async false
            createListUseCase(name)
            updateLists()
            true
        }.asCompletableFuture()
    }

    fun deleteList(name: String) {
        viewModelScope.launch {
            deleteListUseCase(name)
            updateLists()
        }
    }
}