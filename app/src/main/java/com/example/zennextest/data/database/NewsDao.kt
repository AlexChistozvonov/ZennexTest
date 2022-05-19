package com.example.zennextest.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zennextest.data.models.DataNews

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publish_date DESC")
    fun getAllNews(): DataSource.Factory<Int, DataNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<DataNews>)

    @Query("DELETE FROM news")
    fun deleteAllNews()
}
