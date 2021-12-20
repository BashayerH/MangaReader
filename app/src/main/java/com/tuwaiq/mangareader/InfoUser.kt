package com.tuwaiq.mangareader

import com.tuwaiq.mangareader.mangaApi.models.DataManga

data class InfoUser(
    var userName:String ="",
    var emil:String="",
    var imgProfile:String ="",
    var favManga: List<String> = listOf()
        )