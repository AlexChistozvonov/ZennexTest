package com.example.zennextest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zennextest.data.models.DataNews

@Database(entities = [DataNews::class], exportSchema = false, version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract val newsDao: NewsDao
}
