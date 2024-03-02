package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ponomarev.lexify.data.repository.WordsRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class WordsRepositoryModule {

    @Binds
    abstract fun bindWordsRepository(
        wordsRepositoryImpl: WordsRepositoryImpl
    ): WordsRepository
}