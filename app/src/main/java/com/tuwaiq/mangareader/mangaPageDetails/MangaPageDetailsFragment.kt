package com.tuwaiq.mangareader.mangaPageDetails

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
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.databinding.DetailsListBinding
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaApi.models.MangaDetials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.tuwaiq.mangareader.Dialogs.SenstiveDialogFragment
import com.tuwaiq.mangareader.mangaApi.models.Data


private const val TAG = "MangaPageDetailsFragmen"

class MangaPageDetailsFragment : Fragment() {


    private val navArgs by navArgs<MangaPageDetailsFragmentArgs>()
    private val pageDetailsViewModel: MangaPageDetailsViewModel by lazy { ViewModelProvider(this)[MangaPageDetailsViewModel::class.java] }
    private lateinit var binding: MangaPageDetailsFragmentBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)
        navController = findNavController()
        //data that coming from main page
        pageDetailsViewModel.detailsData(navArgs.currentManga!!.id).observe(
            viewLifecycleOwner
        ) {
            it.forEach { itList ->
                //to hide sensitive Material
                val sensitive = arrayListOf(
                    "Adult", "Ecchi", "Doujinshi", "Gender bender", "Josei", "Manhwa",
                    "Mature", "Yaoi", "Yuri", "Smut", "Shounen ai","Harem"
                )
                sensitive.forEach { it ->
                    if (itList.data.genres.contains(it)) {
                        val dilog = SenstiveDialogFragment()
                        fragmentManager?.let { it1 -> dilog.show(it1, "SenstiveDialog") }
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
                    } else {
                        val currentManga = navArgs.currentManga
                        binding.mangaName.text = itList.data.title.toString()
                        binding.genresView.text = itList.data.genres.toString()
                        binding.rating.text = itList.data.rating.toString()
                        binding.statusView2.text = itList.data.status
                        binding.lastUpV.text = itList.data.last_updated
                        binding.decB.setOnClickListener {
                            val currentManga = navArgs.currentManga
                            val action =
                                MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToDescrDialogFragment(
                                    currentManga
                                )
                            navController.navigate(action)
                        }
                        binding.chapter.setOnClickListener {
                            val currentManga = navArgs.currentManga
                            val  action =
                                MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToChaptersFragment(currentManga)
                            navController.navigate(action)
                        }
                        binding.imageD.load(currentManga!!.img)
                        binding.comment.apply {
                            this.setOnClickListener {
                                animate().apply {
                                    duration = 500
                                    // translationXBy(-1000f)
                                    rotationYBy(360f)
                                }.withEndAction {
                                    val action =
                                        MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToCommentsPageFragment(
                                            currentManga = navArgs.currentManga
                                        )

                                    navController.navigate(action)

                                }
                            }

                        }
                        binding.lastCh.setOnClickListener {
                            gotUrl(currentManga.latest_chapter_url)

                        }
                    }
                }

            }
            Log.d(TAG, " current manga id ${navArgs.currentManga!!.id}")
        }
        val currentManga = navArgs.currentManga
        binding.mangaName.text = currentManga!!.title
        binding.imageD.load(currentManga!!.img)
        binding.favB.apply {
            this.setOnClickListener {
                animate().apply {
                    duration = 500
                    //   translationXBy(-1000f)
                    rotationYBy(360f)
                }.withEndAction {
                    val firebaseUser = Constants.firebaseAuth.currentUser!!.uid
                    val person =
                        (DataManga(currentManga.id, currentManga.title, currentManga.img))
                    Log.d(TAG, "fav manga list $person")

                    lifecycleScope.launch(Dispatchers.IO) {

                        val infoUserFav: MutableList<String> =
                            (Constants.infoUserCollection.document(Firebase.auth.currentUser!!.uid)
                                .get()
                                .await()
                                .toObject(InfoUser::class.java)
                                ?.favManga ?: emptyList()).toMutableList()
                        if (!infoUserFav.contains(person.id)) {
                            infoUserFav += person.id
                        }

                        Constants.infoUserCollection.document(firebaseUser)
                            .update("favManga", infoUserFav)
                        //  mangaFavCollection.document(firebaseUser).set(person)
                        val idee = Constants.mangaFavCollection.document().id
                        Constants.mangaFavCollection.document(idee).set(person)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "added to favorite",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "you don't have an account",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }

                    val action =
                        MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToFavoritFragment(
                            currentManga
                        )
                    navController.navigate(action)
                }
            }
        }
        binding.comment.apply {
            this.setOnClickListener {
                animate().apply {
                    duration = 500
                    // translationXBy(-1000f)
                    rotationYBy(360f)
                }.withEndAction {
                    val action =
                        MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToCommentsPageFragment(
                            currentManga = navArgs.currentManga
                        )
                    navController.navigate(action)
                }
            }
        }
        binding.lastCh.setOnClickListener {
            gotUrl(currentManga.latest_chapter_url)
        }
        binding.decB.setOnClickListener {
            val currentManga = navArgs.currentManga
            val action =
                MangaPageDetailsFragmentDirections.actionMangaPageDetailsFragmentToDescrDialogFragment(
                    currentManga
                )
            navController.navigate(action)
        }


        return binding.root
    }


    private inner class DetailsAdapter(val dit: List<MangaDetials>) :
        RecyclerView.Adapter<DetailsAdapter.DetailsHloder>() {
        inner class DetailsHloder(val binding: DetailsListBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(mangaId: MangaDetials) {
                binding.geners.text = mangaId.data.genres.toString()
                binding.lastUp.text = mangaId.data.last_updated
                binding.rating.text = mangaId.data.rating.toString()
                Log.d(TAG, "info ${bind(mangaId)}")
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

    private fun gotUrl(s: String) {
        val uri: Uri = Uri.parse(s)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }


}
