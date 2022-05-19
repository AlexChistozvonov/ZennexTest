package com.example.zennextest.domain.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.zennextest.domain.NetworkStatus

data class PagingBlob(
    val news: LiveData<PagedList<DomainNews>>,
    val networkStatus: LiveData<NetworkStatus>,
    val refreshStatus: LiveData<NetworkStatus>,
    val refresh: () -> Unit)