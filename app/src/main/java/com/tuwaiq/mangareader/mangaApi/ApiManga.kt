package com.tuwaiq.mangareader.mangaApi

import com.tuwaiq.mangareader.mangaApi.models.MangaResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiManga {
    @GET("/api/mangakakalot/browse" +
            "?rapidapi-key=85707d71d8mshb1b402ea65d2e06p1e2a14jsn39b1098fcf93")
    suspend fun getManga():Response<MangaResponse>

}