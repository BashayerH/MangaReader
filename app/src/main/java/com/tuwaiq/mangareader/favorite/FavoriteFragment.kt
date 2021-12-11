package com.tuwaiq.mangareader.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private val favoriteViewModel:FavoriteViewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }

    private lateinit var binding:FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavoriteBinding.inflate(layoutInflater)

        return binding.root
    }


}