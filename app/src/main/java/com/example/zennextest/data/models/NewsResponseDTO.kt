package com.example.zennextest.data.models

class NewsResponseDTO(val articles: List<ArticleDTO>)

data class ArticleDTO(
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?
)
