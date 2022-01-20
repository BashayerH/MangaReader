package com.tuwaiq.mangareader

import com.google.gson.annotations.SerializedName
import com.tuwaiq.mangareader.mangaApi.models.DataManga

data class InfoUser(

    var userName:String ="",
    var emil:String="",
    var imgProfile:String ="",
    var favManga: List<String> = listOf(),
    var uplodedFile:String= ""
        )

