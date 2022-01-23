package com.tuwaiq.mangareader.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.databinding.FavoritFragmentBinding
import com.tuwaiq.mangareader.databinding.FavoritItemBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga

private const val TAG = "FavoriteFragment"

class FavoriteFragment : Fragment() {


    private val favoriteViewModel: FavoriteViewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }
    private lateinit var binding: FavoritFragmentBinding
    private lateinit var navController: NavController
    private val navArgs by navArgs<FavoriteFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.fetchFav(Constants.infoUserCollection.document("favManga").id).observe(
            viewLifecycleOwner, Observer {
                    binding.favoritRv.adapter = FavAdapter(it)


                Log.d(TAG, "from fav $it")
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritFragmentBinding.inflate(layoutInflater)
        binding.favoritRv.layoutManager = LinearLayoutManager(context)
        navController = findNavController()
        var currentUser = Constants.firebaseAuth.currentUser
        loadData(currentUser?.uid.toString())

        binding.refresh.setOnRefreshListener {
            if (binding.refresh.isRefreshing) {
                binding.refresh.isRefreshing = false
            }
            loadData(currentUser!!.uid)
        }

        val swip = object :SwipToDelete(){

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT ->{
                      viewHolder.itemView.visibility = View.GONE
                    }
                }
                val position = viewHolder.adapterPosition
                binding.favoritRv.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swip)
        itemTouchHelper.attachToRecyclerView(binding.favoritRv)



        return binding.root
    }


    inner class FavAdapter(val list: List<DataManga>) :
        RecyclerView.Adapter<FavAdapter.FavHolder>() {
        inner class FavHolder(val binding: FavoritItemBinding) :
            RecyclerView.ViewHolder(binding.root)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
            val binding = FavoritItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return FavHolder(binding)

        }

        override fun onBindViewHolder(holder: FavHolder, position: Int) {


            val currentFav = list[position]
         //   val post = InfoUser()
//            val manId = navArgs.currentManga!!.id

            with(holder) {
                    binding.favTitle.setText(currentFav.title)
                    binding.favImg.load(currentFav.img)
                    binding.checkBoxFav.apply {
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                this.animate().apply {
                                    duration =500
                                    translationZBy(1000f)
                                }.withEndAction {
                                    deleteFav(currentFav)
                                }

                            }
                        }
                    }

                binding.itemFav.setOnClickListener {
                    val action =
                        FavoriteFragmentDirections.actionFavoriteFragmentToMangaPageDetailsFragment(
                            navArgs.currentManga
                        )
                    navController.navigate(action)
                }
            }


        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

     fun FavAdapter.FavHolder.deleteFav(
        currentFav: DataManga
    ) {
        var deleteFav =Constants.mangaFavCollection
            .whereEqualTo("id", currentFav.id)
            .get()
        deleteFav.addOnSuccessListener {
            for (doc in it) {
               Constants.mangaFavCollection.document(doc.id).delete()
                    //here you didnt specify what to delete
                    .addOnSuccessListener {
                        binding.itemFav.removeAllViews()
                        Toast.makeText(
                            context,
                            "the item deleted",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }


    fun loadData(favManga: String) {
        val list = listOf<DataManga>()
       Constants.infoUserCollection
            // mangaFavCollection
            .whereEqualTo("favManga", id)
            .get().addOnSuccessListener {
                if (it != null) {
                    for (doc in it) {
                        var favList = doc.toObject(DataManga::class.java)

                    }
                }
                val swip = object :SwipToDelete(){

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        binding.favoritRv.adapter?.notifyItemRemoved(position)
                    }
                }

            }
    }
}

