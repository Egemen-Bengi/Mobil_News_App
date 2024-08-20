package com.egemenbengi.newsproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class News(
    @ColumnInfo("name")
    @SerializedName("name") val name:String?,

    @ColumnInfo("description")
    @SerializedName("description") val description: String?,

    @ColumnInfo("source")
    @SerializedName("source") val source: String?,

    @ColumnInfo("url")
    @SerializedName("url") val url: String?,

    @ColumnInfo("image")
    @SerializedName("image") val image:String?,

    @ColumnInfo("key")
    @SerializedName("key") val key: String?
){
    @PrimaryKey(autoGenerate = true) var uuid = 0
}