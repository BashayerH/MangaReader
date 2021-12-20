package com.tuwaiq.mangareader.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga

class MainPageViewModel : ViewModel() {

    val repo:MangaRepo = MangaRepo()

  private  val allResult:MutableLiveData<List<DataManga>> = MutableLiveData()
    val result:LiveData<List<DataManga>> = allResult


    val  dataLiveData :LiveData<List<DataManga>> = repo.fetchManga()


}
