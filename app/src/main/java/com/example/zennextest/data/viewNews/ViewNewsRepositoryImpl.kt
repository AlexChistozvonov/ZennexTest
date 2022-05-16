package com.example.zennextest.data.viewNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.room.withTransaction
import com.example.zennextest.core.network.Api
import com.example.zennextest.data.datebase.RoomDateBase
import com.example.zennextest.data.models.DataNews
import com.example.zennextest.domain.DomainNews
import com.example.zennextest.domain.PagingBlob
import com.example.zennextest.domain.viewNews.ViewNewsRepository
import com.example.zennextest.ui.extension.toDataNews
import com.example.zennextest.ui.extension.toDomainNews
import kotlinx.coroutines.*
import java.net.UnknownHostException
import javax.inject.Inject

class ViewNewsRepositoryImpl @Inject constructor(
    private val networkService: Api,
    private val database: RoomDateBase,
) : ViewNewsRepository {

    private lateinit var livePagedList: LiveData<PagedList<DomainNews>>
    private lateinit var scope: CoroutineScope

    override suspend fun getNews(scope: CoroutineScope): PagingBlob {
        this.scope = scope
        val viewNewsPageSource = ViewNewsPageSource(
            scope,
            networkService,
            this::insertIntoDatabase
        )
        val config = Config(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        )

        livePagedList = database.newsDao.getAllNews().map { it.toDomainNews() }
            .toLiveData(
                config,
                initialLoadKey = 1,
                boundaryCallback = viewNewsPageSource
            )

        return PagingBlob(
            news = livePagedList,
            networkStatus = viewNewsPageSource.networkStatus,
        )
    }

    private fun insertIntoDatabase(news: List<DataNews>) {
        scope.launch {
            withContext(Dispatchers.IO) {
                database.withTransaction {
                    database.newsDao.insertNews(news)
                }
            }
        }
    }
}
