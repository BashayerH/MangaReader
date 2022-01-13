package com.tuwaiq.mangareader.mangaApi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataManga(
    val id:String = "",
    val title:String = "",
    @SerializedName("thumbnail_url")
    val img:String = "",
    val latest_chapter:String ="",
    val latest_chapter_url:String ="",
    val latest_chapter_title:String ="",
    val description:String = " ",
    val url :String ="",
    val last_updated:String="",
    val status:String="",
    val genres:String ="",
    val rating :Float = 0F,
     val chapters:String= ""
): Parcelable

