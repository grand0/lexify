package ru.kpfu.itis.ponomarev.lexify.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordAudioModelMapper
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordDefinitionModelMapper
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordEtymologiesModelMapper
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordExampleModelMapper
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordRelatedWordsModelMapper
import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val api: WordApi,
    private val wordAudioModelMapper: WordAudioModelMapper,
    private val wordDefinitionModelMapper: WordDefinitionModelMapper,
    private val wordEtymologiesModelMapper: WordEtymologiesModelMapper,
    private val wordExampleModelMapper: WordExampleModelMapper,
    private val wordRelatedWordsModelMapper: WordRelatedWordsModelMapper,
) : WordRepository{

    override suspend fun getDefinitions(word: String): List<WordDefinitionModel> {
        return withContext(Dispatchers.IO) {
            val data = api.getDefinitions(word)
            data.mapNotNull(wordDefinitionModelMapper::mapDataModelToModel)
        }
    }

    override suspend fun getEtymologies(word: String): WordEtymologiesModel {
        return withContext(Dispatchers.IO) {
            val data = api.getEtymologies(word)
            wordEtymologiesModelMapper.mapDataModelToModel(data)
        }
    }

    override suspend fun getExamples(word: String): List<WordExampleModel> {
        return withContext(Dispatchers.IO) {
            val data = api.getExamples(word)
            data.examples
                .distinctBy { it.text }
                .map(wordExampleModelMapper::mapDataModelToModel)
        }
    }

    override suspend fun getRelatedWords(word: String): List<RelatedWordsModel> {
        return withContext(Dispatchers.IO) {
            val data = api.getRelatedWords(word)
            data.map(wordRelatedWordsModelMapper::mapDataModelToModel)
        }
    }

    override suspend fun getAudio(word: String): List<WordAudioModel> {
        return withContext(Dispatchers.IO) {
            val data = api.getAudio(word)
            data.map(wordAudioModelMapper::mapDataModelToModel)
        }
    }
}