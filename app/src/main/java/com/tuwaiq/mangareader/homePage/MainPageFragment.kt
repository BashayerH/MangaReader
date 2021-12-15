package com.tuwaiq.mangareader.homePage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.MainPageFragmentBinding
import com.tuwaiq.mangareader.databinding.MangaListItemBinding
import com.tuwaiq.mangareader.firebaseAuth
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.register.infoUserCollection


class MainPageFragment : Fragment() {



    private val mainPageViewModel: MainPageViewModel by lazy { ViewModelProvider(this)[MainPageViewModel::class.java] }
    private lateinit var binding: MainPageFragmentBinding
    private lateinit var navController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater)
         binding.recyclerManga.setIntervalRatio(.5f)

      //  binding.mangaRv.layoutManager = GridLayoutManager(requireContext(),2)
        navController = findNavController()


       // NavigationUI.setupWithNavController(binding.drawerNavLayout,navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPageViewModel.dataLiveData.observe(
            viewLifecycleOwner,
           Observer {
                binding.recyclerManga.adapter = MangaAdapter(it)
            }
        )
    }

    private inner class MangaHolder(val binding: MangaListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(story:DataManga){

            binding.mangaImg.load(story.img)

            binding.mangaTitle.text = story.title
            binding.lastCh.text = story.latest_chapter_title
            binding.mangaImg.setOnClickListener {
               val bundle = Bundle()
                bundle.putString("title",binding.mangaTitle.toString())
                navController.navigate(R.id.mangaPageDetailsFragment,bundle)
//                arguments?.putSerializable("title",story.title)
//                arguments?.putSerializable("img",story.img)
//                arguments?.putSerializable("latest_chapter_title",story.latest_chapter_title)
            }
        }
    }

    private inner class MangaAdapter(val manga:List<DataManga>):RecyclerView.Adapter<MangaHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHolder {
          val  binding = MangaListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MangaHolder(binding)
        }

        override fun onBindViewHolder(holder: MangaHolder, position: Int) {
            val mangas = manga[position]
            holder.bind(mangas)
        }

        override fun getItemCount(): Int = manga.size


    }

}