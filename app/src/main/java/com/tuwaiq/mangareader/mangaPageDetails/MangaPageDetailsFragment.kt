package com.tuwaiq.mangareader.mangaPageDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding

class MangaPageDetailsFragment : Fragment() {



    private  val pageDetailsViewModel: MangaPageDetailsViewModel by lazy { ViewModelProvider(this)[MangaPageDetailsViewModel::class.java] }
    private lateinit var binding:MangaPageDetailsFragmentBinding
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

        }



        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    //try add to fav
    fun addToFav(){
        val timeSt =System.currentTimeMillis()



    }



}