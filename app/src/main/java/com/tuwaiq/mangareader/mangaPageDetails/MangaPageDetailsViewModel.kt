package com.tuwaiq.mangareader.mangaPageDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.mangaApi.models.DataManga

class MangaPageDetailsViewModel : ViewModel() {

    val repo: MangaRepo = MangaRepo()

    fun detailsData(): LiveData<List<DataManga>> = liveData {
                  emit(repo.detailsManga(idM = ""))
    }

}