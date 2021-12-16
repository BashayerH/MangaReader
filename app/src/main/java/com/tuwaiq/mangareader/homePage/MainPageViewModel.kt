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

   // never used
//    fun getTheData(){
//        viewModelScope.launch {
//            repo.fetchMangaFlow.collect {
//                _result.value = it
//            }
//
//    }
//
//}
    val  dataLiveData :LiveData<List<DataManga>> = repo.fetchManga()


}
