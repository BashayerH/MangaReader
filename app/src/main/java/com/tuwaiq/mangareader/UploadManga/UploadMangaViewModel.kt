package com.tuwaiq.mangareader.UploadManga

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import com.tuwaiq.mangareader.modelsCatg.CatgMangData
import com.tuwaiq.mangareader.modelsCatg.RepoCatg

class UploadMangaViewModel : ViewModel() {

    val catRepo:RepoCatg = RepoCatg()

    val getCatg:LiveData<List<CatgMangData>> = catRepo.getCatg()
}