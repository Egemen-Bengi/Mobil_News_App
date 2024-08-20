package com.egemenbengi.newsproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.egemenbengi.newsproject.model.News
import com.egemenbengi.newsproject.roomdb.NewsDatabase
import kotlinx.coroutines.launch

class NewsDetailViewModel(application: Application) : AndroidViewModel(application) {
    val news = MutableLiveData<News>()

    fun getLiveData(uuid: Int){
        val dao = NewsDatabase(getApplication()).newsDao()
        viewModelScope.launch {
            val selectedNews = dao.findElementById(uuid)
            news.value = selectedNews
        }
    }
}