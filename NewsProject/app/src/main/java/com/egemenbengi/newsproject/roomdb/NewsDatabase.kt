package com.egemenbengi.newsproject.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.egemenbengi.newsproject.model.News
import kotlin.concurrent.Volatile

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object{
        @Volatile
        private var instance: NewsDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "NewsDatabase"
        ).build()
    }
}