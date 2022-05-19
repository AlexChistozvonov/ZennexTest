package com.example.zennextest.ui.extension

import com.example.zennextest.data.models.ArticleDTO
import com.example.zennextest.data.models.DataNews
import com.example.zennextest.domain.models.DomainNews

/**
 * Converts Data model into Domain model
 * */
fun DataNews.toDomainNews(): DomainNews {
    return DomainNews(
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        page
    )
}

/**
 * Converts list of network DTO into list of Database models
 * */
fun List<ArticleDTO>.toDataNews(page: Int): List<DataNews> {
    return map {
        DataNews(
            it.title ?: "<No title>",
            it.description ?: "<No description>",
            it.url,
            it.urlToImage,
            it.publishedAt?.parseServerDate(),
            page
        )
    }
}

/**
 * Parses the string received from server
 * */
fun String.parseServerDate(): String {
    val newsDate = this.split("T")
    val date = newsDate[0].split("-").reversed().joinToString("-")
    val time = newsDate[1].split(":")
    return "$date  ${time[0]}:${time[1]}"
}