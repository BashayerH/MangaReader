package com.tuwaiq.mangareader.mangaApi.models

import com.google.gson.annotations.SerializedName

data class DataManga(
    val id:String = "",
    val title:String = "",
@SerializedName("thumbnail_url")
    val img:String = "",
    val latest_chapter:String ="",
    val latest_chapter_url:String ="",
    val description:String = " ",
    val url :String =""


)
