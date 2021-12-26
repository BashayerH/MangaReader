package com.tuwaiq.mangareader.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.databinding.FavoritFragmentBinding
import com.tuwaiq.mangareader.databinding.FavoritItemBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga

private const val TAG = "FavoriteFragment"
class FavoriteFragment : Fragment() {


    private val favoriteViewModel: FavoriteViewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding:FavoritFragmentBinding
    private lateinit var navController: NavController
    var infoUserCollection = Firebase.firestore.collection("InfoUser")
    var mangaFavCollection=Firebase.firestore.collection("FavMangaUser")

    private val navArgs by navArgs<FavoriteFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.fetchFav(navArgs.currentManga!!.id).observe(
            viewLifecycleOwner, Observer {
                binding.favoritRv.adapter = FavAdapter(it)
                Log.d(TAG,"from fav $it")
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritFragmentBinding.inflate(layoutInflater)
        binding.favoritRv.layoutManager=LinearLayoutManager(context)

        var currentUser = firebaseAuth.currentUser
    loadData(currentUser?.uid.toString())


        return binding.root
    }



    private inner class FavAdapter(val list:List<DataManga>):RecyclerView.Adapter<FavAdapter.FavHolder>(){
        inner class FavHolder(val binding:FavoritItemBinding):RecyclerView.ViewHolder(binding.root)

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
            with(holder){
                binding.favTitle.setText(currentFav.title)
               binding.favImg.load(currentFav.img)
            }

        }

        override fun getItemCount(): Int {
          return  list.size
        }
    }



    fun loadData(favManga:String){
        val list = listOf<DataManga>()
        //infoUserCollection
                mangaFavCollection
           // .whereEqualTo("FavMangaUser",id)
            .get().addOnSuccessListener {
            if (it!=null){
                for (doc in it){
                    var favList = doc.toObject(DataManga::class.java)

                }
            }
//            binding.favoritRv.apply {
//                layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
//                adapter = FavAdapter(list)
            }
        }
    }

