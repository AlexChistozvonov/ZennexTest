package com.example.zennextest.core.di

import com.example.zennextest.data.viewNews.ViewNewsRepositoryImpl
import com.example.zennextest.domain.viewNews.ViewNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun loginRepository(
        loginRepository: ViewNewsRepositoryImpl
    ): ViewNewsRepository
}
