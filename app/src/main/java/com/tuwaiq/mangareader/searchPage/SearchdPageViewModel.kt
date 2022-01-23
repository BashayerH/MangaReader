package com.tuwaiq.mangareader.searchPage

import androidx.lifecycle.*
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga

private const val TAG = "DownloadPageViewModel"
class DownloadPageViewModel : ViewModel() {


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
                    emit(Constants.repo.randomManga())
                }else{
                    emit(Constants.repo.searchManga(it))
                }
            }


        }
    }
 }