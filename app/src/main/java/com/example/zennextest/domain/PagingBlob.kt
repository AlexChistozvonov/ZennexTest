package com.example.zennextest.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.zennextest.presentation.NetworkStatus

data class PagingBlob(
    val news: LiveData<PagedList<DomainNews>>,
    val networkStatus: LiveData<NetworkStatus>,)