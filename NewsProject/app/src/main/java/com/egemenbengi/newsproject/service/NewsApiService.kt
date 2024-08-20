package com.egemenbengi.newsproject.service

import com.egemenbengi.newsproject.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.collectapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

    suspend fun getData(tag: String): NewsResponse {
        return try{
            retrofit.getNews(
                apiKey = "apikey 7bh6JLHiDGDFUNwmVeFfwS:5jbT8HaFsDNlQik9DSVO4k",
                country = "tr",
                tag = tag,
            )
        } catch (exception: Exception){
            throw exception
        }
    }
}