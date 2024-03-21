package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.CheckDefinitionInListUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetAllListsUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.model.ListSelectorModel
import javax.inject.Inject

@HiltViewModel
class ListsSelectorViewModel @Inject constructor(
    private val getAllListsUseCase: GetAllListsUseCase,
    private val checkDefinitionInListUseCase: CheckDefinitionInListUseCase,
) : ViewModel() {

    private val _listSelectorsState = MutableStateFlow<List<ListSelectorModel>>(listOf())
    val listSelectorsState get() = _listSelectorsState.asStateFlow()

    fun updateListSelectors(id: String) {
        viewModelScope.launch {
            val lists = getAllListsUseCase()
            lists.map { list ->
                ListSelectorModel(
                    name = list.name,
                    isChecked = checkDefinitionInListUseCase(id, list.name),
                )
            }.let {
                _listSelectorsState.value = it
            }
        }
    }
}