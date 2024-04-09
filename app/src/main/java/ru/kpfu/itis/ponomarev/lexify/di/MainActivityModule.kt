package ru.kpfu.itis.ponomarev.lexify.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigatorImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityModule {

    @Binds
    @ActivityScoped
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}
