package com.tuwaiq.mangareader.mangaPageDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
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
            binding.lastChTxtView2.setText(currentManga.latest_chapter_title)
            binding.descTxtView.setText(currentManga.description)
        }else{
            Toast.makeText(requireContext(),"some thing wrong in load information", Toast.LENGTH_LONG).show()
        }

        binding.favBtn.setOnClickListener{
            val firebaseUser = firebaseAuth.currentUser!!.uid
            val person= (DataManga(currentManga!!.id,currentManga.title,currentManga.img))
            Log.d(TAG,"fav manga list $person")
            mangaFavCollection.document(firebaseUser)
                .set(person)
                //.update("FavMangaUser",person)
                .addOnSuccessListener {
                    Toast.makeText(context,"added to favorite success",Toast.LENGTH_LONG).show()
                    Log.d(TAG,"fav manga list $mangaFavCollection")
                }
            // addToFav()
            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoritFragment(currentManga)
            navController.navigate(action)
        }
//        binding.favBtn.setOnClickListener {
//            val firebaseUser = firebaseAuth.currentUser!!.uid
//            val person = InfoUser(favManga= mutableListOf(currentManga!!.id))
//
//            infoUserCollection.document(firebaseUser)
//                .update("favManga",person.favManga)
//                //.set(person )
//                .addOnSuccessListener {
//                    Toast.makeText(context,"added to favorit sucess",Toast.LENGTH_LONG).show()
//
//                    Log.d(TAG,"fav manga list $infoUserCollection")
//                }
//
//            // addToFav()
//            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoritFragment(currentManga)
//            navController.navigate(action)
//            }

        binding.readBtn.setOnClickListener {

            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToReadWebVeiwFragment(currentManga)
            navController.navigate(action)
        }

        return  binding.root
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