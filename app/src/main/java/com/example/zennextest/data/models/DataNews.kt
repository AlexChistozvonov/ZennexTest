package com.example.zennextest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class DataNews(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "desc") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image_url") val urlToImage: String?,
    @ColumnInfo(name = "publish_date") val publishedAt: String?,
    @ColumnInfo(name = "page") val page: Int
)
