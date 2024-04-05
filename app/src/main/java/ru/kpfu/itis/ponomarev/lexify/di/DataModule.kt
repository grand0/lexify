package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ponomarev.lexify.data.repository.ListsRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.data.repository.LovedRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.data.repository.WordRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.data.repository.WordsRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindWordRepository(
        wordRepository: WordRepositoryImpl,
    ): WordRepository

    @Binds
    @Singleton
    abstract fun bindWordsRepository(
        wordsRepositoryImpl: WordsRepositoryImpl
    ): WordsRepository

    @Binds
    @Singleton
    abstract fun bindListsRepository(
        listsRepositoryImpl: ListsRepositoryImpl
    ): ListsRepository

    @Binds
    @Singleton
    abstract fun bindLovedRepository(
        lovedRepositoryImpl: LovedRepositoryImpl
    ): LovedRepository
}