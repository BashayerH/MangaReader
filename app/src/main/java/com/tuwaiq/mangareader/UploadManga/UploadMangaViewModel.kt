package com.tuwaiq.mangareader.UploadManga

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.core.Repo
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.modelsCatg.CatgMangData
import com.tuwaiq.mangareader.modelsCatg.RepoCatg
import kotlinx.coroutines.launch

class UploadMangaViewModel : ViewModel() {

    val catRepo:RepoCatg = RepoCatg()
    val repo:MangaRepo = MangaRepo()

    val getCatg:LiveData<List<CatgMangData>> = catRepo.getCatg()


    fun mangaPdf(mangaPdf: Uri) {
        viewModelScope.launch {
            repo.uploadPdf(mangaPdf)
        }
    }
}