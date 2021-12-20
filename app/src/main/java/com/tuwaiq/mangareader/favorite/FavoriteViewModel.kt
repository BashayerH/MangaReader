package com.tuwaiq.mangareader.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuwaiq.mangareader.mangaApi.MangaRepo

class FavoriteViewModel: ViewModel() {


    val repo: MangaRepo = MangaRepo()

}