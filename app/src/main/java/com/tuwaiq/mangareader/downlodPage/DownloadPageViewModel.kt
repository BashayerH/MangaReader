package com.tuwaiq.mangareader.downlodPage

import android.util.Log
import androidx.lifecycle.*
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "DownloadPageViewModel"
class DownloadPageViewModel : ViewModel() {

    val repo: MangaRepo = MangaRepo()


    private val searchTermLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        searchTermLiveData.value = ""
    }

    fun setSearch(query:String) {

        searchTermLiveData.value = query
        }
    fun mangaSearch():LiveData<List<DataManga>>{


        val tempLiveData:MutableLiveData<List<DataManga>> = MutableLiveData()

        return Transformations.switchMap(searchTermLiveData){
            liveData{
                if (it.isBlank()){
                    emit(repo.fetchManga())
                }else{
                    emit(repo.searchManga(it))
                }
            }


        }
    }


//    val  dataLiveDataPopular :LiveData<List<DataManga>> =  liveData {
//        emit(repo.fetchManga())
//    }

   // val  setSearch :LiveData<List<DataManga>> = repo.searchManga(query = "Attack")

 }