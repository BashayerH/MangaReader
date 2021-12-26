package com.tuwaiq.mangareader.mangaApi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MangaRepo"
open class MangaRepo {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var infoUserCollection = Firebase.firestore.collection("InfoUser")
    var mangaFavCollection=Firebase.firestore.collection("FavMangaUser")


    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://grabr-dev.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val mangaApi : ApiManga = retrofit.create(ApiManga::class.java)


//fun getFav()=CoroutineScope(Dispatchers.IO).launch {
//    val querySnapshot = infoUserCollection.get().await()
//
//    for (doc in querySnapshot.documents){
//        val person =doc.toObject(InfoUser::class.java)
//    }
//
//}



    suspend fun getFav(currentUser: String): List<DataManga> {
        val person= InfoUser()
      //  currentUser.let{
          var x = mangaFavCollection
               // .whereEqualTo("favManga",person.favManga)
                .get()
                .await()
              .toObjects(DataManga::class.java)

     //   }
        return x
   }

//       liveData  (Dispatchers.IO){
//           val person= InfoUser()
//           val datalist = infoUserCollection
//               .whereEqualTo("favManga",person.favManga)
//               .get().await()
//           if (datalist != null){
//               emit(datalist.toObjects(DataManga::class.java))
//
//           }
//       }
//   }


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