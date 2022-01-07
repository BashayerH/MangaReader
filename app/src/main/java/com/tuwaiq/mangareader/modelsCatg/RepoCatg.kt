package com.tuwaiq.mangareader.modelsCatg

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tuwaiq.mangareader.mangaApi.ApiManga
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RepoCatg"
class RepoCatg {

    private val catgRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://grabr-dev.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val catgApi : ApiManga = catgRetrofit.create(ApiManga::class.java)


    fun getCatg(): LiveData<List<CatgMangData>> {
        return liveData (Dispatchers.IO){
            val catgResp = catgApi.getCatogManga()
            if (catgResp.isSuccessful){
                Log.e(TAG,"it's work ${catgResp.body()}")
                catgResp.body()?.listCatgut?.let { emit(it) }
            }else{
                Log.e(TAG,"there is an error ${catgResp.errorBody()}")
            }
        }
    }
}