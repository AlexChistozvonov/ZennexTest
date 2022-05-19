package com.example.zennextest.di

import com.example.zennextest.data.network.Api
import com.example.zennextest.data.network.ConnectivityInterceptor
import com.example.zennextest.ui.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun okHttpLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return logging
    }

    @Provides
    @Singleton
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(ConnectivityInterceptor())
        .build()


    @Provides
    @Singleton
    fun networkService(
        okHttpClient: OkHttpClient,
        moshiBuilder: Moshi
    ): Api =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .build()
            .create(Api::class.java)

    @Provides
    @Singleton
    fun moshiBuilder(): Moshi.Builder = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
}
