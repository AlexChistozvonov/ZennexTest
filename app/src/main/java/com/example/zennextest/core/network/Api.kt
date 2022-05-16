package com.example.zennextest.core.network

import com.example.zennextest.data.models.NewsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("everything?q=ios&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907")
    suspend fun getFirstViewNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 5
    ): Response<NewsResponseDTO>
}
