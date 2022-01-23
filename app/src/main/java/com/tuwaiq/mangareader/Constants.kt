package com.tuwaiq.mangareader


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.mangareader.mangaApi.MangaRepo

object  Constants {


    val repo = MangaRepo()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var infoUserCollection = Firebase.firestore.collection("InfoUser")
    var mangaFavCollection = Firebase.firestore.collection("FavMangaUser")
    var commentCollection = Firebase.firestore.collection("CommentManga")
    var imgFile = FirebaseStorage.getInstance().getReference("/photos/${firebaseAuth.currentUser?.uid}")
    var mangaUpRef = Firebase.storage.reference.child("/pdfManga/${firebaseAuth.currentUser?.uid}")
    const val   Work_ID =   "1"
    const val KEY_LANG="key_lang"
    const val PHOTO =0
    const val REQUEST_PDF =0

}