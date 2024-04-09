package ru.kpfu.itis.ponomarev.lexify.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordsApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.RandomWordModelMapper
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper.WordOfTheDayModelMapper
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val api: WordsApi,
    private val randomWordModelMapper: RandomWordModelMapper,
    private val wordOfTheDayModelMapper: WordOfTheDayModelMapper,
) : WordsRepository {

    override suspend fun getRandomWord(): RandomWordModel {
        return withContext(Dispatchers.IO) {
            val data = api.getRandomWord()
            randomWordModelMapper.mapDataModelToModel(data)
        }
    }

    override suspend fun getRandomWords(limit: Int): List<RandomWordModel> {
        return withContext(Dispatchers.IO) {
            val data = api.getRandomWords(limit)
            data.map(randomWordModelMapper::mapDataModelToModel)
        }
    }

    override suspend fun getWordOfTheDay(): WordOfTheDayModel {
        return withContext(Dispatchers.IO) {
            val data = api.getWordOfTheDay()
            wordOfTheDayModelMapper.mapDataModelToModel(data)
        }
    }
}