package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.AddDefinitionUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.CheckDefinitionInListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.DeleteDefinitionUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetAllListsUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel
import ru.kpfu.itis.ponomarev.lexify.util.set
import javax.inject.Inject

@HiltViewModel
class RememberDefinitionViewModel @Inject constructor(
    private val getAllListsUseCase: GetAllListsUseCase,
    private val checkDefinitionInListUseCase: CheckDefinitionInListUseCase,
    private val addDefinitionUseCase: AddDefinitionUseCase,
    private val deleteDefinitionUseCase: DeleteDefinitionUseCase,
) : ViewModel() {

    private val _listSelectorsState = MutableStateFlow<List<ListSelectorModel>>(listOf())
    val listSelectorsState get() = _listSelectorsState.asStateFlow()

    fun switch(def: WordDefinitionModel, listName: String) {
        viewModelScope.launch {
            val ind = _listSelectorsState.value.indexOfFirst { it.name == listName }
            if (ind != -1) {
                val state = _listSelectorsState.value[ind].copy(isLoading = true)
                _listSelectorsState[ind] = state.copy()
                if (_listSelectorsState.value[ind].isChecked) {
                    deleteDefinitionUseCase(def.id, listName)
                    state.isChecked = false
                } else {
                    addDefinitionUseCase(def, listName)
                    state.isChecked = true
                }
                state.isLoading = false
                _listSelectorsState[ind] = state
            }
        }
    }

    fun updateAllLists(id: String) {
        viewModelScope.launch {
            val lists = getAllListsUseCase()
            _listSelectorsState.value = lists.map { list ->
                ListSelectorModel(
                    name = list.name,
                    isChecked = false,
                    isLoading = true,
                )
            }
            _listSelectorsState.value = lists.map { list ->
                ListSelectorModel(
                    name = list.name,
                    isChecked = checkDefinitionInListUseCase(id, list.name),
                    isLoading = false,
                )
            }
        }
    }
}