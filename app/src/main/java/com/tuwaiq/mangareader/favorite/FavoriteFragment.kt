package com.tuwaiq.mangareader.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.databinding.FavoritItemBinding
import com.tuwaiq.mangareader.databinding.FragmentFavoriteBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga


class FavoriteFragment : Fragment() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val db:FirebaseFirestore = FirebaseFirestore.getInstance()

    private val favoriteViewModel:FavoriteViewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }

    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var databaseReference:DatabaseReference
    private lateinit var navController: NavController
    val firebaseUser = firebaseAuth.currentUser!!.uid
    var infoUserCollection = Firebase.firestore.collection("InfoUser")


    private val navArgs by navArgs <FavoriteFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavoriteBinding.inflate(layoutInflater)
        binding.favoritRv.layoutManager = LinearLayoutManager(requireContext())
        navController=findNavController()

        return binding.root
    }

    private inner class FavoriteHolder(val binding:FavoritItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(currentFav: DataManga) {
            binding.favImg.load(currentFav.img)
            binding.favTitle.setText(currentFav.title)

        }
    }

    private inner class FavoriteAdapter(val fav:List<DataManga>):RecyclerView.Adapter<FavoriteHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
            val binding = FavoritItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return FavoriteHolder(binding)
        }

        override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
          val currentFav = fav[position]
          holder.bind(currentFav)

        }

        override fun getItemCount(): Int = fav.size

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.favLiveData().observe(
            viewLifecycleOwner, Observer {
                binding.favoritRv.adapter = FavoriteAdapter(it)
            }
        )
    }

}

//    fun getAllFav(favManga: String) {
//        val person=InfoUser()
//        infoUserCollection
//            .whereEqualTo("favManga",person.favManga)
//            .get()
//            .addOnSuccessListener {
//
//        }
//
//
//    }
