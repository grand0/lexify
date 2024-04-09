package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.gson.gson
import ru.kpfu.itis.ponomarev.lexify.BuildConfig
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordsApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.impl.WordApiImpl
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.impl.WordsApiImpl
import ru.kpfu.itis.ponomarev.lexify.util.Keys
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindWordApi(wordApi: WordApiImpl): WordApi

    @Binds
    @Singleton
    abstract fun bindWordsApi(wordApi: WordsApiImpl): WordsApi

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return HttpClient(Android) {
                install(ContentNegotiation) {
                    gson()
                }
                install(Resources)
                install(DefaultRequest)

                if (BuildConfig.DEBUG) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.BODY
                    }
                }

                expectSuccess = true

                defaultRequest {
                    url(BuildConfig.WORDNIK_API_BASE_URL)
                    url {
                        parameters.append(Keys.API_KEY_KEY, BuildConfig.WORDNIK_API_KEY)
                    }
                }
            }
        }
    }
}