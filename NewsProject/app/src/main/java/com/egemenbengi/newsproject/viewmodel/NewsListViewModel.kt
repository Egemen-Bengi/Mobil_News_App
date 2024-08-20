package com.egemenbengi.newsproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.egemenbengi.newsproject.model.News
import com.egemenbengi.newsproject.roomdb.NewsDatabase
import com.egemenbengi.newsproject.service.NewsApiService
import com.egemenbengi.newsproject.util.MySharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    val listNews = MutableLiveData<List<News>>()
    val progressBar = MutableLiveData<Boolean>()

    private val newsApiService = NewsApiService()
    private val mySharedPreferences = MySharedPreferences(getApplication())
    private val timer = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(tag: String){
        val dataTime = mySharedPreferences.getTime()
        if (dataTime != null && dataTime != 0L && System.nanoTime() - dataTime < timer){
            getDataFromRoom()
        } else {
            getDataFromInternet(tag)
        }
    }

    fun refreshDataFromInternet(tag: String){
        getDataFromInternet(tag)
    }

    private fun getDataFromRoom(){
        progressBar.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val newsList = NewsDatabase(getApplication()).newsDao().getAllNews()
            withContext(Dispatchers.Main){
                showNews(newsList)
            }
        }
    }

    private fun getDataFromInternet(tag: String){
        progressBar.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val news = newsApiService.getData(tag)
            withContext(Dispatchers.Main){
                progressBar.value = false
                saveToRoom(news.result)
            }
        }
    }

    private fun saveToRoom(newsList: List<News>){
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAllNews()
            val idList = dao.insertAllNews(*newsList.toTypedArray())
            var i = 0
            while (i < idList.size){
                newsList[i].uuid =idList[i].toInt()
                i++
            }
            showNews(newsList)
        }
        mySharedPreferences.saveTime(System.nanoTime())
    }

    private fun showNews(newsList: List<News>){
        listNews.value = newsList
        progressBar.value = false
    }
}