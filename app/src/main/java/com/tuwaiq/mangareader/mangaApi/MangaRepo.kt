package com.tuwaiq.mangareader.mangaApi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MangaRepo"
open class MangaRepo {



    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://grabr-dev.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val mangaApi : ApiManga = retrofit.create(ApiManga::class.java)



    fun fetchManga ():LiveData<List<DataManga>> {
        return liveData (Dispatchers.IO ) {
            val response = mangaApi.getManga()
            if (response.isSuccessful){
                Log.e(TAG,"it's work ${response.body()}")
                response.body()?.data?.let { emit(it) }
            }else{
                Log.e(TAG,"there is an error ${response.errorBody()}")
            }

        }
    }
}