package com.tuwaiq.mangareader.mangaPageDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.service.autofill.UserData
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.LoginData
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import java.util.*

class MangaPageDetailsFragment : Fragment() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


     var infoUserCollection = Firebase.firestore.collection("InfoUser")
    private  val pageDetailsViewModel: MangaPageDetailsViewModel by lazy { ViewModelProvider(this)[MangaPageDetailsViewModel::class.java] }
    private lateinit var binding:MangaPageDetailsFragmentBinding
    private lateinit var databaseReference: DatabaseReference
    private val navArgs by navArgs <MangaPageDetailsFragmentArgs>()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)

        navController = findNavController()
        //provide the information for selected manga
        val currentManga = navArgs.currentManga
        if (currentManga != null) {
            binding.nameTxtView.setText(currentManga.title)
            binding.imageViewDetil.load(currentManga.img)
            binding.lastChTxtView2.setText(currentManga.latest_chapter_title)
            binding.descTxtView.setText(currentManga.description)
        }else{
            Toast.makeText(requireContext(),"some thing wrong in load information", Toast.LENGTH_LONG).show()
        }

        binding.favBtn.setOnClickListener {
            val firebaseUser = firebaseAuth.currentUser!!.uid
            val person = InfoUser(favManga = listOf(currentManga!!.id))

            infoUserCollection.document(firebaseUser.toString())
                .update("favManga", person.favManga  )
                .addOnSuccessListener {
                Toast.makeText(context,"added to favorit sucess",Toast.LENGTH_LONG).show()
            }
            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoriteFragment(currentManga)
            navController.navigate(action)
            addToFav()
        }



        return  binding.root

    }


    //try add to favorite
    fun addToFav(){
       // val timeSt =System.currentTimeMillis()
        databaseReference = FirebaseDatabase.getInstance().getReference("favManga")
        val reference = binding.nameTxtView.text
        firebaseAuth.uid?.let {
            databaseReference.child(it).setValue(reference).addOnSuccessListener {


            }
        }





    }



}