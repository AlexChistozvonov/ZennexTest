package com.example.zennextest.data.viewNews

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.zennextest.core.network.Api
import com.example.zennextest.data.models.DataNews
import com.example.zennextest.domain.DomainNews
import com.example.zennextest.presentation.NetworkStatus
import com.example.zennextest.ui.extension.toDataNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class ViewNewsPageSource @Inject constructor(
    private val scope: CoroutineScope,
    private val service: Api,
    private val handleResponseCallback: (List<DataNews>) -> Unit
    ) : PagedList.BoundaryCallback<DomainNews>() {

    private var currentPage = 1
    val networkStatus = MutableLiveData(NetworkStatus.Success)

    override fun onItemAtEndLoaded(itemAtEnd: DomainNews) {
        currentPage = itemAtEnd.page + 1
        fetchNews()
    }

    override fun onZeroItemsLoaded() {
        currentPage = 1
        fetchNews()
    }

    private fun fetchNews() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val response = service.getFirstViewNews(currentPage)
                try {
                    networkStatus.postValue(NetworkStatus.Loading)
                    if (response.isSuccessful) {
                        val news = response.body()?.articles?.toDataNews(currentPage)
                        news?.let { handleResponseCallback(it) }
                        networkStatus.postValue(NetworkStatus.Success)
                    }
                } catch (e: UnknownHostException) {
                    networkStatus.postValue(NetworkStatus.Error)
                }
            }
        }
    }
}
