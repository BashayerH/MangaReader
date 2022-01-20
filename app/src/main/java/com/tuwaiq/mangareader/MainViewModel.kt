package com.tuwaiq.mangareader

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.mangareader.mangaApi.MangaRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val repo = MangaRepo()



    fun uploadPhoto(imageUri: Uri) {
        viewModelScope.launch {
            repo.changePhoto(imageUri)
        }
    }

    private val _isLoding = MutableStateFlow(true)
    val isLoding = _isLoding.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoding.value = false
        }
    }
}