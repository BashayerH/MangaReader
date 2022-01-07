package com.tuwaiq.mangareader.mangaPageDetails

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

private const val TAG = "MangaPageDetailsFragmen"
class MangaPageDetailsFragment : Fragment() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val navArgs by navArgs <MangaPageDetailsFragmentArgs>()
     var infoUserCollection = Firebase.firestore.collection("InfoUser")
    var mangaFavCollection=Firebase.firestore.collection("FavMangaUser")

    private  val pageDetailsViewModel: MangaPageDetailsViewModel by lazy { ViewModelProvider(this)[MangaPageDetailsViewModel::class.java] }
    private lateinit var binding:MangaPageDetailsFragmentBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)
         val currentManga = navArgs.currentManga
        navController = findNavController()
        //provide the information for selected manga

        if (currentManga != null) {
            binding.nameTxtView.text = currentManga.title
            binding.imageViewDetil.load(currentManga.img)
           // binding.    lastChTxtView2.setText(currentManga.latest_chapter_title)
            binding.descTxtView.setText(currentManga.description)
        }else{
            Toast.makeText(requireContext(),"some thing wrong in load information", Toast.LENGTH_LONG).show()
        }

        binding.favBtn.setOnClickListener{
            val firebaseUser = firebaseAuth.currentUser!!.uid
            val person= (DataManga(currentManga!!.id,currentManga.title,currentManga.img))
            Log.d(TAG,"fav manga list $person")

        lifecycleScope.launch(Dispatchers.IO){

    val infoUserFav:MutableList<String> = (infoUserCollection.document(Firebase.auth.currentUser!!.uid)
        .get()
        .await()
        .toObject(InfoUser::class.java)
        ?.favManga ?: emptyList()) as MutableList<String>
    if (!infoUserFav.contains(person.id)){
        infoUserFav+= person.id
    }

    infoUserCollection.document(firebaseUser).update("favManga",infoUserFav)
          //  mangaFavCollection.document(firebaseUser).set(person)
               val idee=     mangaFavCollection.document().id
            mangaFavCollection.document(idee).set(person)


//دي حتنفع للكومنت
//        .addOnSuccessListener {
//                    Toast.makeText(context,"added to favorite success",Toast.LENGTH_LONG).show()
//                    Log.d(TAG,"fav manga list $mangaFavCollection")
//                }
}



//            mangaFavCollection
//                .document(UUID.randomUUID().toString())
//                //.update("favManga",person)
//                .set(person)
//
//
            // addToFav()
            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoritFragment(currentManga)
            navController.navigate(action)
        }

         binding.commentBtn.setOnClickListener {
             val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToCommentsPageFragment(currentManga = navArgs.currentManga)
             navController.navigate(action)

         }


        binding.readBtn.setOnClickListener {
        //    gotUrl(currentManga!!.latest_chapter_url)




//            val url = currentManga?.latest_chapter_url
//            val request = DownloadManager.Request(Uri.parse(url))
//                .setTitle("File")
//                .setDescription("Downloading....")
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                .setAllowedOverMetered(true)
//              //for download the file..
//            val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            dm.enqueue(request)
//
//
//            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToReadWebVeiwFragment(currentManga)
//            navController.navigate(action)
        }

        return  binding.root
    }
    private fun gotUrl(s:String){
        val uri:Uri = Uri.parse(s)
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)

    }



//    fun addToFav(){
//        val firebaseUser = firebaseAuth.currentUser!!.uid
//        val person = InfoUser(favManga = mutableListOf(currentManga!!.id))
//
//        infoUserCollection.document(firebaseUser)
//          //  .update("favManga", person.favManga  )
//            .addSnapshotListener{value,error ->
//                if (value != null) {
//                    value.data?.forEach{
//                        when(it.key){
//                            "favManga" -> person.favManga.text(it.value.toString())
//                        }
//                    }
//                }
//
//            }
//        }



}