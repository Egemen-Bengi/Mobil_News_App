package com.egemenbengi.newsproject.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.concurrent.Volatile

class MySharedPreferences {
    companion object{
        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: MySharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createMySharedPreferences(context).also {
                instance = it
            }
        }

        private fun createMySharedPreferences(context: Context): MySharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return MySharedPreferences()
        }
    }
    fun saveTime(time: Long){
        sharedPreferences?.edit()?.putLong(TIME, time)?.apply()
    }

    fun getTime() = sharedPreferences?.getLong(TIME, 0)
}