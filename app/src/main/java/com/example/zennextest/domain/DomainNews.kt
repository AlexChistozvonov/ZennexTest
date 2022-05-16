package com.example.zennextest.domain

data class DomainNews (
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val page: Int
)