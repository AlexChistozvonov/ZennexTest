package com.example.zennextest.data.network

import com.example.zennextest.data.models.NewsResponseDTO
import com.example.zennextest.ui.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(Constants.API_KEY)
    suspend fun getFirstViewNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 5
    ): Response<NewsResponseDTO>
}
