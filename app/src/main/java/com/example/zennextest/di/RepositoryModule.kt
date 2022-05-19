package com.example.zennextest.di

import com.example.zennextest.data.repository.NewsRepositoryImpl
import com.example.zennextest.domain.repository.NewsRepository
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
        loginRepository: NewsRepositoryImpl
    ): NewsRepository
}
