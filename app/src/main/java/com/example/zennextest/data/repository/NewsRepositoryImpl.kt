package com.example.zennextest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.room.withTransaction
import com.example.zennextest.data.network.Api
import com.example.zennextest.data.database.RoomDataBase
import com.example.zennextest.data.models.DataNews
import com.example.zennextest.domain.NetworkStatus
import com.example.zennextest.domain.models.DomainNews
import com.example.zennextest.domain.models.PagingBlob
import com.example.zennextest.domain.repository.NewsRepository
import com.example.zennextest.presentation.pagination.NewsPageSource
import com.example.zennextest.ui.extension.toDataNews
import com.example.zennextest.ui.extension.toDomainNews
import kotlinx.coroutines.*
import java.net.UnknownHostException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val networkService: Api,
    private val database: RoomDataBase,
) : NewsRepository {

    private lateinit var livePagedList: LiveData<PagedList<DomainNews>>
    private lateinit var scope: CoroutineScope

    override suspend fun getNews(scope: CoroutineScope): PagingBlob {
        this.scope = scope
        val viewNewsPageSource = NewsPageSource(
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

        val refreshTrigger = MutableLiveData<Unit?>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }

        return PagingBlob(
            news = livePagedList,
            networkStatus = viewNewsPageSource.networkStatus,
            refresh = {
                refreshTrigger.value = null
            },
            refreshStatus = refreshState
        )
    }

    private fun refresh(): LiveData<NetworkStatus> {
        val networkStatus = MutableLiveData(NetworkStatus.Loading)

        scope.launch {
            withContext(Dispatchers.IO) {
                try {
                    networkStatus.postValue(NetworkStatus.Loading)
                    val response = networkService.getFirstViewNews(1)
                    if (response.isSuccessful) {
                        val news = response.body()?.articles?.toDataNews(1)
                        news?.let {
                            database.runInTransaction {
                                database.newsDao.deleteAllNews()
                                database.newsDao.insertNews(news)
                            }
                        }
                        livePagedList.value?.dataSource?.invalidate()
                        networkStatus.postValue(NetworkStatus.Success)
                    }
                } catch (e: UnknownHostException) {
                    networkStatus.postValue(NetworkStatus.Error)
                }
            }
        }

        return networkStatus
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
