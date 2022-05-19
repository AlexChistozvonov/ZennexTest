package com.example.zennextest.presentation.viewNews

import androidx.lifecycle.*
import androidx.paging.*
import com.example.zennextest.domain.models.DomainNews
import com.example.zennextest.domain.models.PagingBlob
import com.example.zennextest.domain.repository.NewsRepository
import com.example.zennextest.domain.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val viewNewsRepository: NewsRepository) :
    ViewModel() {

    private lateinit var result: LiveData<PagingBlob>
    private val job = CoroutineScope(Job() + Dispatchers.IO)

    init {
        viewModelScope.launch {
            result = MutableLiveData(viewNewsRepository.getNews(job))
        }
    }

    val networkStatus: LiveData<NetworkStatus> =
        Transformations.switchMap(result) { it.networkStatus }
    val news: LiveData<PagedList<DomainNews>> = Transformations.switchMap(result) { it.news }
    val refreshStatus: LiveData<NetworkStatus> =
        Transformations.switchMap(result) { it.refreshStatus }

    fun refresh() {
        result.value?.refresh?.invoke()
    }
}
