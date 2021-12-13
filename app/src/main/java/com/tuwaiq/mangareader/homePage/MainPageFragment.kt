package com.tuwaiq.mangareader.homePage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
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
import com.tuwaiq.mangareader.mangaApi.models.DataManga


class MainPageFragment : Fragment() {



    private val mainPageViewModel: MainPageViewModel by lazy { ViewModelProvider(this)[MainPageViewModel::class.java] }
    private lateinit var binding: MainPageFragmentBinding
    private lateinit var navController: NavController


    private val navArgs:MainPageFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("fromMainPage","the email ${navArgs.emil} ")

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater)
        binding.mangaRv.layoutManager = GridLayoutManager(requireContext(),2)
        navController = findNavController()
//for argument try to teke the user name to disply it in profile
        val args = MainPageFragmentArgs.fromBundle(requireArguments())
        args.emil

       // NavigationUI.setupWithNavController(binding.drawerNavLayout,navController)
        return binding.root
    }

    private inner class MangaHolder(val binding: MangaListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(story:DataManga){
            binding.mangaImg.load(story.img)
            binding.mangaTitle.text = story.title
            binding.lastCh.text = story.latest_chapter_url
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