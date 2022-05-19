package com.example.zennextest.di

import android.content.Context
import androidx.room.Room
import com.example.zennextest.data.database.RoomDataBase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDataBase {
        return Room.databaseBuilder(
            appContext,
            RoomDataBase::class.java,
            "RssReader"
        ).build()
    }
}
