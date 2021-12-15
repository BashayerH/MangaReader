package com.tuwaiq.mangareader.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.mangareader.mangaApi.ApiManga
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.launch

class MainPageViewModel : ViewModel() {

    val repo:MangaRepo = MangaRepo()

  private  val _result:MutableLiveData<List<DataManga>> = MutableLiveData()
    val result:LiveData<List<DataManga>> = _result

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
