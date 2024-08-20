package com.egemenbengi.newsproject.service

import com.egemenbengi.newsproject.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {
    @GET("news/getNews?")
    suspend fun getNews(
        @Header("Authorization") apiKey: String,
        @Query("country") country: String,
        @Query("tag") tag: String
    ):NewsResponse
}