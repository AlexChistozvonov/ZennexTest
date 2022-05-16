package com.example.zennextest.domain.viewNews

import com.example.zennextest.domain.PagingBlob
import kotlinx.coroutines.CoroutineScope

interface ViewNewsRepository {
    suspend fun getNews(scope: CoroutineScope): PagingBlob
}
