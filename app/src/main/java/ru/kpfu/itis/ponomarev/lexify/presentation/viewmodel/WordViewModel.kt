package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.AddDefinitionUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.lists.DeleteDefinitionUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.AddLovedUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.CheckIfWordIsLovedUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.loved.DeleteLovedUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.word.GetRelatedWordsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.word.GetWordAudioUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.word.GetWordDefinitionsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.word.GetWordEtymologiesUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.word.GetWordExamplesUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionExceptionHandler
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase,
    private val getWordEtymologiesUseCase: GetWordEtymologiesUseCase,
    private val getWordExamplesUseCase: GetWordExamplesUseCase,
    private val getRelatedWordsUseCase: GetRelatedWordsUseCase,
    private val getWordAudioUseCase: GetWordAudioUseCase,
    private val addDefinitionUseCase: AddDefinitionUseCase,
    private val deleteDefinitionUseCase: DeleteDefinitionUseCase,
    private val checkIfWordIsLovedUseCase: CheckIfWordIsLovedUseCase,
    private val deleteLovedUseCase: DeleteLovedUseCase,
    private val addLovedUseCase: AddLovedUseCase,
    private val exceptionHandler: DictionarySectionExceptionHandler,
) : ViewModel() {

    private val _definitionsState = MutableStateFlow<List<WordDefinitionModel>?>(null)
    val definitionsState get() = _definitionsState.asStateFlow()

    private val _etymologiesState = MutableStateFlow<WordEtymologiesModel?>(null)
    val etymologiesState get() = _etymologiesState.asStateFlow()

    private val _examplesState = MutableStateFlow<List<WordExampleModel>?>(null)
    val examplesState get() = _examplesState.asStateFlow()

    private val _relatedWordsState = MutableStateFlow<List<RelatedWordsModel>?>(null)
    val relatedWordsState get() = _relatedWordsState.asStateFlow()

    private val _audioState = MutableStateFlow<List<WordAudioModel>?>(null)
    val audioState get() = _audioState.asStateFlow()

    private val _isLovedState = MutableStateFlow(false)
    val isLovedState get() = _isLovedState.asStateFlow()

    var isUpdated = false
        private set

    val errorsChannel = Channel<Throwable>(Channel.UNLIMITED)

    fun update(word: String) {
        updateIsLoved(word)
        updateDefinitions(word)
        updateEtymologies(word)
        updateExamples(word)
        updateRelatedWords(word)
        updateAudio(word)
        isUpdated = true
    }

    fun updateDefinitions(word: String) {
        viewModelScope.launch {
            try {
                _definitionsState.value = getWordDefinitionsUseCase(word)
            } catch (e: Throwable) {
                errorsChannel.send(exceptionHandler.handleException(e, DictionarySection.DEFINITIONS))
            }
        }
    }

    fun updateEtymologies(word: String) {
        viewModelScope.launch {
            try {
                _etymologiesState.value = getWordEtymologiesUseCase(word)
            } catch (e: Throwable) {
                errorsChannel.send(exceptionHandler.handleException(e, DictionarySection.ETYMOLOGIES))
            }
        }
    }

    fun updateExamples(word: String) {
        viewModelScope.launch {
            try {
                _examplesState.value = getWordExamplesUseCase(word)
            } catch (e: Throwable) {
                errorsChannel.send(exceptionHandler.handleException(e, DictionarySection.EXAMPLES))
            }
        }
    }

    fun updateRelatedWords(word: String) {
        viewModelScope.launch {
            try {
                _relatedWordsState.value = getRelatedWordsUseCase(word)
            } catch (e: Throwable) {
                errorsChannel.send(exceptionHandler.handleException(e, DictionarySection.RELATED))
            }
        }
    }

    fun updateAudio(word: String) {
        viewModelScope.launch {
            try {
                _audioState.value = getWordAudioUseCase(word)
            } catch (e: Throwable) {
                errorsChannel.send(exceptionHandler.handleException(e, DictionarySection.AUDIO))
            }
        }
    }

    fun rememberDefinition(def: WordDefinitionModel, listName: String, word: String) {
        viewModelScope.launch {
            addDefinitionUseCase(def, listName, word)
        }
    }

    fun forgetDefinition(id: String, listName: String) {
        viewModelScope.launch {
            deleteDefinitionUseCase(id, listName)
        }
    }

    fun updateIsLoved(word: String) {
        viewModelScope.launch {
            _isLovedState.value = checkIfWordIsLovedUseCase(word)
        }
    }

    fun unlove(word: String) {
        viewModelScope.launch {
            deleteLovedUseCase(word)
            updateIsLoved(word)
        }
    }

    fun love(word: String) {
        viewModelScope.launch {
            addLovedUseCase(word)
            updateIsLoved(word)
        }
    }
}