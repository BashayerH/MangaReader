package com.tuwaiq.mangareader.mangaApi

import android.net.Uri
import android.util.Log
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.mangaApi.models.Data
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaApi.models.MangaDetials
import kotlinx.coroutines.tasks.await

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val TAG = "MangaRepo"
open class MangaRepo() {




    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://grabr-dev.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val mangaApi : ApiManga = retrofit.create(ApiManga::class.java)


    suspend fun getFav(currentUser: String): List<DataManga> {
          var x = Constants.mangaFavCollection
                .get()
                .await()
              .toObjects(DataManga::class.java)
        return x
   }



    suspend fun getComment(com: String):List<CommentData>{
    val comm = Constants.commentCollection
        .get()
        .await()
        .toObjects(CommentData::class.java)
        return comm
    }

    suspend fun showCommManga(com: String){
       Constants.commentCollection
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
                return emptyList()

            }

        return result
    }

    suspend fun randomManga ():List<DataManga> {
        var result:List<DataManga>
        val response = mangaApi.getRandomM()
        if (response.isSuccessful){
            Log.e(TAG,"it's work ${response.body()}")
            result = response.body()?.data!!
        }else{
            Log.e(TAG,"there is an error ${response.errorBody()}")
            return emptyList()
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
               Log.e(TAG,"it's work with search77 ${response}")
                if (response.isSuccessful){
                    Log.e(TAG," from repo search ${response.body()}")
                   result =  response.body()?.data ?: emptyList()
                }else{
                    Log.e(TAG,"there is an error in search from repo ${response.errorBody()}")

                }
       return result

    }


    suspend fun detailsMangaById(idM:String):List<MangaDetials>{
      var details:List<MangaDetials>  = emptyList()
        var result:List<DataManga> = emptyList()
        val response = mangaApi.detailsManga(idM)
        val errorCase = mangaApi.getRandomM()
        Log.e(TAG,"it's work with details77 ${response}")
        if (response.isSuccessful){
            details = listOf(response.body()!!)
            Log.e(TAG,"it's work with details ${response.body()!!.data}")
       }
       else{
           result = (errorCase.body()!!.data) as List<DataManga>
            Log.e(TAG,"there is an error with details in repo ${response.errorBody()!!.string()}")
        }
        return details
    }

    suspend fun changePhoto(imgUri:Uri){

        val ref = Constants.imgFile.putFile(imgUri).await()

        if (ref.task.isComplete){
            val upload = ref.storage.downloadUrl.await()
           Constants.infoUserCollection.document(Constants.firebaseAuth.currentUser!!.uid)
                .update("imgProfile",upload.toString())


        }

    }

    suspend fun uploadPdf(mangaPdf:Uri):List<String>{

        val refPdf = Constants.mangaUpRef.putFile(mangaPdf).await()

        if (refPdf.task.isComplete){
            val upload = refPdf.storage.downloadUrl.await()
           Constants.infoUserCollection.document(Constants.firebaseAuth.currentUser!!.uid)
                .update("uplodedFile",upload.toString())


        }
        return emptyList()
    }

}