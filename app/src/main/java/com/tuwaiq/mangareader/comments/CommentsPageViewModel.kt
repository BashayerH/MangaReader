package com.tuwaiq.mangareader.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.mangaApi.MangaRepo

class CommentsPageViewModel : ViewModel() {

    val repo: MangaRepo = MangaRepo()


     fun getComments(com: String):LiveData<List<CommentData>> = liveData {
        emit(repo.getComment(com))
    }

    fun showComment(com: String)= liveData {
        emit(repo.showCommManga(com))
    }
}