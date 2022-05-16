package com.example.zennextest.data.datebase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zennextest.data.models.DataNews
import com.example.zennextest.data.models.NewsDao

@Database(entities = [DataNews::class], exportSchema = false, version = 1)
abstract class RoomDateBase : RoomDatabase() {
    abstract val newsDao: NewsDao
}
