package com.tuwaiq.mangareader.mangaApi

import com.tuwaiq.mangareader.mangaApi.models.MangaResponse
import com.tuwaiq.mangareader.modelsCatg.ResponseCatg
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiManga {
    @GET("api/mangakakalot/browse?rapidapi-key=85707d71d8mshb1b402ea65d2e06p1e2a14jsn39b1098fcf93")
    suspend fun getManga():Response<MangaResponse>

    @GET("api/mangakakalot/genres?rapidapi-key=85707d71d8mshb1b402ea65d2e06p1e2a14jsn39b1098fcf93")
    suspend fun getCatogManga():Response<ResponseCatg>

    @GET("api/mangakakalot/search?rapidapi-key=85707d71d8mshb1b402ea65d2e06p1e2a14jsn39b1098fcf93")
     suspend fun searchForManga(@Query("keyword") keyword:String): Response<MangaResponse>


    @GET("api/mangakakalot/popular?rapidapi-key=85707d71d8mshb1b402ea65d2e06p1e2a14jsn39b1098fcf93")
    suspend fun getpopular():Response<MangaResponse>
}





