package com.egemenbengi.newsproject.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.egemenbengi.newsproject.model.News

@Dao
interface NewsDao {
    @Insert
    suspend fun insertAllNews(vararg news: News): List<Long>
    //eklenen haberlerin id'lerini long listesi olarak döndürür

    @Query("DELETE FROM News")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM News")
    suspend fun getAllNews(): List<News>

    @Query("SELECT * FROM News WHERE uuid = :uuid")
    suspend fun findElementById(uuid: Int): News
}