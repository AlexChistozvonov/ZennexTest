package com.example.zennextest.domain.repository

import com.example.zennextest.domain.models.PagingBlob
import kotlinx.coroutines.CoroutineScope

interface NewsRepository {
    suspend fun getNews(scope: CoroutineScope): PagingBlob
}
