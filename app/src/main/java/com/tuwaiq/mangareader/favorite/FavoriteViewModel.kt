package com.tuwaiq.mangareader.favorite

import androidx.lifecycle.*
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
       val repo: MangaRepo = MangaRepo()

 //   val favLiveData:LiveData<List<DataManga>> = repo.getFav()


private val favList:MutableLiveData<List<DataManga>> = MutableLiveData()

    fun favLiveData() :LiveData<List<DataManga>> {

        viewModelScope.launch  (Dispatchers.IO){
            repo.getFav()

        }

        return favList
    }



}