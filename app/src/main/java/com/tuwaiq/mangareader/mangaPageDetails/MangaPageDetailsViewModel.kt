package com.tuwaiq.mangareader.mangaPageDetails

import android.util.Log
import androidx.lifecycle.*
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.Data
import com.tuwaiq.mangareader.mangaApi.models.MangaDetials

private const val TAG = "MangaPageDetailsViewMod"

class MangaPageDetailsViewModel : ViewModel() {

    fun detailsData(id:String): LiveData<List<MangaDetials>> = liveData {
                  emit(Constants.repo.detailsMangaById(idM = id))
        Log.d(TAG,"from VMD ${this}")
    }

}