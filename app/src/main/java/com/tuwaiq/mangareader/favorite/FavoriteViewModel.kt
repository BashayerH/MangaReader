package com.tuwaiq.mangareader.favorite

import android.util.Log
import androidx.lifecycle.*
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavoriteViewModel"
class FavoriteViewModel : ViewModel() {


    //   val favLiveData:LiveData<List<DataManga>> = repo.getFav()


    private val _favList= MutableLiveData<List<DataManga>>()
    val liveDataFav:LiveData<List<DataManga>>
    get()= _favList




    fun fetchFav(currentUser: String):LiveData<List<DataManga>> = liveData {

        emit(Constants.repo.getFav(currentUser))
    }





//    fun favLiveData() : LiveData<List<DataManga>> {
//        viewModelScope.launch  (Dispatchers.IO){
//            (repo.getFav())
//
//        }
//
//
//        return favList
//    }
}