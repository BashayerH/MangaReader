package com.tuwaiq.mangareader.mangaApi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaApi.models.MangaResponse
import com.tuwaiq.mangareader.modelsCatg.CatgMangData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MangaRepo"
open class MangaRepo() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var infoUserCollection = Firebase.firestore.collection("InfoUser")
    var mangaFavCollection=Firebase.firestore.collection("FavMangaUser")
    var commentCollection = Firebase.firestore.collection("CommentManga")


    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://grabr-dev.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val mangaApi : ApiManga = retrofit.create(ApiManga::class.java)


    suspend fun getFav(currentUser: String): List<DataManga> {
          var x = mangaFavCollection

                .get()
                .await()
              .toObjects(DataManga::class.java)
        return x
   }



    suspend fun getComment(com: String):List<CommentData>{
    val comm = commentCollection
        .get()
        .await()
        .toObjects(CommentData::class.java)
        return comm
    }

    suspend fun showCommManga(com: String){
        commentCollection
            .add(com).await()

    }


  suspend fun fetchManga ():List<DataManga> {
            var result:List<DataManga> = emptyList()
            val response = mangaApi.getManga()
            if (response.isSuccessful){
                Log.e(TAG,"it's work ${response.body()}")
               result = response.body()?.data!!
            }else{
                Log.e(TAG,"there is an error ${response.errorBody()}")
            }

        return result
    }

//     fun searchManga(query:String): List<MangaResponse> {
////        Log.e(TAG," from repo search ${searchManga(query)} ")
//        return mangaApi.searchForManga(query)
//
//    }

    suspend fun searchManga(query:String): List<DataManga>{

               var result:List<DataManga> = emptyList()
                val response = mangaApi.searchForManga(query)
                if (response.isSuccessful){
                    Log.e(TAG," from repo search ${response.body()}")
                   result =  response.body()?.data ?: emptyList()
                }else{

                    Log.e(TAG,"there is an error in search from repo ${response.errorBody()}")
                }
       return result

    }

    suspend fun detailsManga(idM:String):List<DataManga>{
        var details:List<DataManga> = emptyList()
        val response = mangaApi.detailsManga(idM)
        if (response.isSuccessful){
            Log.e(TAG,"it's work with details ${response.body()}")
            details = response.body()?.data!!
        }else{
            Log.e(TAG,"there is an error with details in repo ${response.errorBody()!!.string()}")
        }
        return details
    }

}