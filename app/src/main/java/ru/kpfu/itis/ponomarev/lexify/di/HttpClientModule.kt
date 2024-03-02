package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.gson.gson
import ru.kpfu.itis.ponomarev.lexify.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                gson()
            }
            install(Resources)
            install(DefaultRequest)

            defaultRequest {
                url("https://api.wordnik.com/v4/")
                url {
                    parameters.append("api_key", BuildConfig.WORDNIK_API_KEY)
                }
            }
        }
    }
}