package com.tuwaiq.mangareader.comments

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.mangareader.mangaApi.MangaRepo

class CommentsPageViewModel : ViewModel() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = firebaseAuth.currentUser?.uid
    val repo: MangaRepo = MangaRepo()


     fun getComments(com: String):LiveData<List<CommentData>> = liveData {
        emit(repo.getComment(com))
    }

    fun showComment(com: String)= liveData {
        emit(repo.showCommManga(com))
    }

    fun getPhoto(img:String = firebaseUser.toString()):LiveData<Uri>{
        val imgRef= FirebaseStorage.getInstance().getReference("/photos/$img").downloadUrl

        val imgLiveData: MutableLiveData<Uri> = MutableLiveData()
        imgRef.addOnSuccessListener {
            imgLiveData.value = it
        }
    return imgLiveData
    }

}


