package ru.kpfu.itis.ponomarev.lexify.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ponomarev.lexify.data.local.db.LexifyDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): LexifyDatabase {
        return Room.databaseBuilder(
            context,
            LexifyDatabase::class.java,
            "lexify-db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}