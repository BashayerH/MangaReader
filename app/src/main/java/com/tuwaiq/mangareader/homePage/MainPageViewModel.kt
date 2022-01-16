package com.tuwaiq.mangareader.homePage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaApi.models.MangaResponse
import retrofit2.Response

class MainPageViewModel : ViewModel() {

    val repo:MangaRepo = MangaRepo()

//  private  val allResult:MutableLiveData<List<DataManga>> = MutableLiveData()
//    val result:LiveData<List<DataManga>> = allResult


   // suspend fun  dataLiveData() :List<DataManga> = repo.fetchManga()

   // suspend fun setSearch(query:String): Response<MangaResponse> = repo.searchManga(query)
    fun dataLiveData():LiveData<List<DataManga>> = liveData {

        emit(repo.fetchManga())

   }



}
