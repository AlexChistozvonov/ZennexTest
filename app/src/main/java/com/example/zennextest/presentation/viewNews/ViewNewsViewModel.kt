package com.example.zennextest.presentation.viewNews

import androidx.lifecycle.*
import androidx.paging.*
import com.example.zennextest.domain.DomainNews
import com.example.zennextest.domain.PagingBlob
import com.example.zennextest.domain.viewNews.ViewNewsRepository
import com.example.zennextest.presentation.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ViewNewsViewModel @Inject constructor(private val viewNewsRepository: ViewNewsRepository) :
    ViewModel() {

    private lateinit var result: LiveData<PagingBlob>
    private val job = CoroutineScope(Job() + Dispatchers.IO)

    init {
        viewModelScope.launch {
            result = MutableLiveData(viewNewsRepository.getNews(job))
        }
    }

    val networkStatus: LiveData<NetworkStatus> = Transformations.switchMap(result) { it.networkStatus }
    val news: LiveData<PagedList<DomainNews>> = Transformations.switchMap(result) { it.news }
}
