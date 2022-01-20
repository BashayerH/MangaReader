package com.tuwaiq.mangareader.mangaPageDetails

import android.app.AlertDialog
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.databinding.DetailsListBinding
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaApi.models.MangaDetials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import android.content.DialogInterface
import android.graphics.drawable.DrawableContainer
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import coil.clear
import com.bumptech.glide.load.resource.drawable.DrawableResource
import com.tuwaiq.mangareader.Dialogs.SenstiveDialogFragment
import com.tuwaiq.mangareader.Dialogs.SignOutDialogFragment


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


        pageDetailsViewModel.detailsData(navArgs.currentManga!!.id).observe(
            viewLifecycleOwner) {
            it.forEach { itList ->
                val currentManga = navArgs.currentManga
                val senstive = arrayListOf("Adult","Ecchi" ,"Doujinshi","Gender bender","Josei","Manhwa",
                    "Mature","Yaoi","Yuri","Smut","Shounen ai","")
                senstive.forEach { it ->
                    if (itList.data.genres.contains(it)){
                        var dilog = SenstiveDialogFragment()
                        fragmentManager?.let { it1 -> dilog.show(it1,"SenstiveDialog") }
                        binding.genresView.visibility = View.GONE
                        binding.rating.visibility = View.GONE
                        binding.chapter.visibility = View.GONE
                        binding.decB.visibility = View.GONE
                        binding.favB.visibility = View.GONE
                        binding.lastCh.visibility = View.GONE
                        binding.chapter.visibility = View.GONE
                        binding.comment.visibility = View.GONE
                        binding.imageD.visibility = View.GONE
                        binding.statusView2.visibility = View.GONE
                        binding.lastUpV.visibility = View.GONE
                    }else{
                        binding.genresView.setText(itList.data.genres.toString())
                        binding.rating.setText(itList.data.rating.toString())
                       // binding.chapter.setText(itList.data.chapters.toString())
                        binding.statusView2.setText(itList.data.status)
                        binding.lastUpV.setText(itList.data.last_updated)
                        binding.decB.setOnClickListener {
                            val currentManga = navArgs.currentManga
                            var desc = itList.data.description.toString()

                            val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToDescrDialogFragment(currentManga,desc = "")
                            navController.navigate(action)
                        }
                    }
                }

            }
            Log.d(TAG," current manga id ${navArgs.currentManga!!.id}")
        }

        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)
      //  binding.detRv.layoutManager = LinearLayoutManager(context)
        val currentManga = navArgs.currentManga
        val mangaId = currentManga!!.id
        navController = findNavController()
      //  provide the information for selected manga
        binding.mangaName.setText(currentManga.title)
      //  binding.nameTxtView.text = currentManga.title
        binding.imageD.load(currentManga.img)





        binding.favB.apply {
            this. setOnClickListener {
                animate().apply {
                    duration =500
                 //   translationXBy(-1000f)
                     rotationYBy(360f)
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

        binding.comment.apply {
           this. setOnClickListener {
                animate().apply {
                    duration =500
                   // translationXBy(-1000f)
                    rotationYBy(360f)
                }.withEndAction {
                    val action = MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToCommentsPageFragment(currentManga = navArgs.currentManga)

                    navController.navigate(action)

                }
            }

        }


        binding.lastCh.setOnClickListener {
            gotUrl(currentManga!!.latest_chapter_url)
//
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

//        pageDetailsViewModel.detailsData(navArgs.currentManga!!.id).observe(
//            viewLifecycleOwner) {
////                binding.detRv.adapter = DetailsAdapter(it)
//            it.forEach {
//                binding.gen.setText(it.data.genres.toString())
//                binding.rating.setText(it.data.rating.toString())
//                binding.decsBtn.setText(it.data.description)
//            }
//
//                Log.d(TAG," current manga id ${navArgs.currentManga!!.id}")
//            Log.d(TAG," current manga info D ${it}")
//
//            }

    }


    private inner class DetailsAdapter(val dit: List<MangaDetials>) :
        RecyclerView.Adapter<DetailsAdapter.DetailsHloder>() {
        inner class DetailsHloder(val binding: DetailsListBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(mangaId:MangaDetials){
                    binding.geners.setText(mangaId.data.genres.toString())
                    binding.lastUp.setText(mangaId.data.last_updated)
                    binding.rating.setText(mangaId.data.rating.toString())
                    Log.d(TAG,"info ${bind(mangaId)}")
                }


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
            holder.bind(query)

        }

        override fun getItemCount(): Int = dit.size
    }

    private fun gotUrl(s:String){
        val uri:Uri = Uri.parse(s)
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)

    }


}