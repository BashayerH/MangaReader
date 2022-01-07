package com.tuwaiq.mangareader.homePage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
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
import androidx.work.*
import coil.load
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.WorkManager
import com.tuwaiq.mangareader.databinding.MainPageFragmentBinding
import com.tuwaiq.mangareader.databinding.MangaListItemBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.register.infoUserCollection
import java.util.concurrent.TimeUnit


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
         binding.recyclerManga.getCarouselLayoutManager()
     //   worker()
        myWorkerManager()

      //  binding.mangaRv.layoutManager = GridLayoutManager(requireContext(),2)
        navController = findNavController()


       // NavigationUI.setupWithNavController(binding.drawerNavLayout,navController)
        return binding.root
    }

    private fun myWorkerManager() {
        val constraint = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()
        val myRequest = PeriodicWorkRequest.Builder(
            WorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraint)
            .build()
        context?.let {
            androidx.work.WorkManager.getInstance(it)
                .enqueueUniquePeriodicWork(
                    "my_id",
                    ExistingPeriodicWorkPolicy.KEEP,
                    myRequest
                )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPageViewModel.dataLiveData().observe(
            viewLifecycleOwner,
           Observer {
                   binding.recyclerManga.adapter = MangaAdapter(it)
            }
        )
    }

    private inner class MangaHolder(val binding: MangaListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(currentManga:DataManga){

            binding.mangaImg.load(currentManga.img)

            binding.mangaTitle.text = currentManga.title
            binding.mangaImg.setOnClickListener {
                val action = MainPageFragmentDirections.actionMainPageFragmentToMangaPageDetailsFragment(currentManga)
                navController.navigate(action)
//
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
            val currentManga = manga[position]
            holder.bind(currentManga)
        }

        override fun getItemCount(): Int = manga.size


    }

    private fun worker(){
        val workRequest:WorkRequest = OneTimeWorkRequestBuilder<WorkManager>()
            .build()

        context?.let {
            androidx.work.WorkManager.getInstance(it)
                .enqueue(workRequest)
        }
    }

}