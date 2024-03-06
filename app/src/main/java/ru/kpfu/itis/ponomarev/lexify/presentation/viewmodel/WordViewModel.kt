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
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.GetRelatedWordsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.GetWordAudioUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.GetWordDefinitionsUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.GetWordEtymologiesUseCase
import ru.kpfu.itis.ponomarev.lexify.domain.usecase.GetWordExamplesUseCase
import ru.kpfu.itis.ponomarev.lexify.presentation.exception.DictionarySectionException
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

    val errorsChannel = Channel<Throwable>()

    fun update(word: String) {
        updateDefinitions(word)
        updateEtymologies(word)
        updateExamples(word)
        updateRelatedWords(word)
        updateAudio(word)
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
}