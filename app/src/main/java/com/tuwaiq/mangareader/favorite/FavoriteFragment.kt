package com.tuwaiq.mangareader.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private val favoriteViewModel:FavoriteViewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }

    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var databaseReference:DatabaseReference
    private lateinit var navController: NavController

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

       val db:FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = db.getReference()

        navController=findNavController()
//        val favManga = navArgs.currentManga

        return binding.root
    }


}