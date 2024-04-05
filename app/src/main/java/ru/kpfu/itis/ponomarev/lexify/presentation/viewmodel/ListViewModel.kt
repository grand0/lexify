package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.DeleteDefinitionUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.GetDefinitionsOfListUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getDefinitionsOfListUseCase: GetDefinitionsOfListUseCase,
    private val deleteDefinitionUseCase: DeleteDefinitionUseCase,
) : ViewModel() {

    private val _listDefinitionsState = MutableStateFlow<Map<String, List<ListDefinitionModel>>?>(null)
    val listDefinitionsState get() = _listDefinitionsState.asStateFlow()

    fun updateListDefinitions(listName: String) {
        viewModelScope.launch {
            _listDefinitionsState.value = getDefinitionsOfListUseCase(listName)
                .groupBy(ListDefinitionModel::word)
        }
    }

    fun deleteDefinition(id: String, listName: String) {
        viewModelScope.launch {
            deleteDefinitionUseCase(id, listName)
            updateListDefinitions(listName)
        }
    }
}