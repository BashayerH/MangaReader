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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.tuwaiq.mangareader.databinding.DetailsListBinding
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.databinding.SearchItemBinding
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






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)
      //  binding.detailRV.layoutManager = LinearLayoutManager(context)
        val currentManga = navArgs.currentManga
        val mangaId = currentManga!!.id
        navController = findNavController()
        //provide the information for selected manga

        binding.nameTxtView.text = currentManga.title
        binding.imageViewDetil.load(currentManga.img)

        binding.decsBtn.setOnClickListener {
                val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToDescrDialogFragment(currentManga)
            navController.navigate(action)
        }

        binding.favBtn.apply {
            this. setOnClickListener {
                animate().apply {
                    duration =500
                    translationXBy(-1000f)
                    // rotationYBy(360f)
                }.withEndAction{
                    val firebaseUser = firebaseAuth.currentUser!!.uid
                    val person =
                        (DataManga(currentManga!!.id, currentManga.title, currentManga.img))
                    Log.d(TAG, "fav manga list $person")

                    lifecycleScope.launch(Dispatchers.IO) {

                        val infoUserFav: MutableList<String> =
                            (infoUserCollection.document(Firebase.auth.currentUser!!.uid)
                                .get()
                                .await()
                                .toObject(InfoUser::class.java)
                                ?.favManga ?: emptyList()) as MutableList<String>
                        if (!infoUserFav.contains(person.id)) {
                            infoUserFav += person.id
                        }

                        infoUserCollection.document(firebaseUser).update("favManga", infoUserFav)
                        //  mangaFavCollection.document(firebaseUser).set(person)
                        val idee = mangaFavCollection.document().id
                        mangaFavCollection.document(idee).set(person)
                            .addOnSuccessListener {
                                Toast.makeText(context,"added to favorite",Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context,"you don't have an account",Toast.LENGTH_LONG).show()
                            }
                    }

                    val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoritFragment(currentManga)
                    navController.navigate(action)
                }
        }}





        binding.commentBtn.apply {
           this. setOnClickListener {
                animate().apply {
                    duration =500
                    translationXBy(-1000f)
                   // rotationYBy(360f)
                }.withEndAction {
                    val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToCommentsPageFragment(currentManga = navArgs.currentManga)

                    navController.navigate(action)

                }
            }

        }


        binding.lastChBtn.setOnClickListener {
            gotUrl(currentManga!!.latest_chapter_url)


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        pageDetailsViewModel.detailsData().observe(
//            viewLifecycleOwner, {
//                binding.detailRV.adapter = DetailsAdapter(it)
//                Log.d(TAG," current manga id ${navArgs.currentManga!!.id}")
//            }
//        )

    }


    private inner class DetailsAdapter(val dit: List<DataManga>) :
        RecyclerView.Adapter<DetailsAdapter.DetailsHloder>() {
        inner class DetailsHloder(val binding: DetailsListBinding) :
            RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHloder {
            val binding = DetailsListBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return DetailsHloder(binding)
        }

        override fun onBindViewHolder(holder: DetailsHloder, position: Int) {
            val query = dit[position]
            with(holder) {

                binding.geners.setText(query.genres)
                binding.lastUp.setText(query.last_updated)
                binding.rating.setText(query.rating.toString())


            }
        }

        override fun getItemCount(): Int = dit.size
    }

    private fun gotUrl(s:String){
        val uri:Uri = Uri.parse(s)
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)

    }


}