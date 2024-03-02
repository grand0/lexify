package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ponomarev.lexify.data.repository.WordRepositoryImpl
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class WordRepositoryModule {

    @Binds
    abstract fun bindWordRepository(
        wordRepository: WordRepositoryImpl,
    ) : WordRepository
}