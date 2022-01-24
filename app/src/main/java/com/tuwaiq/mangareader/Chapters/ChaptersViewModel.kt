package com.tuwaiq.mangareader.Chapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.mangaApi.models.MangaDetials

class ChaptersViewModel : ViewModel() {


    fun detailsData(id:String): LiveData<List<MangaDetials>> = liveData {
        emit(Constants.repo.detailsMangaById(idM = id))

    }
}