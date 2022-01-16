package com.tuwaiq.mangareader.searchPage

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.tuwaiq.mangareader.databinding.SearchItemBinding
import com.tuwaiq.mangareader.databinding.SearchPageFragmentBinding
import com.tuwaiq.mangareader.homePage.MainPageFragmentDirections
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "downloadPageFragment"

class downloadPageFragment : Fragment() {


    private val downViewModel: DownloadPageViewModel by lazy { ViewModelProvider(this)[DownloadPageViewModel::class.java] }
    lateinit var binding: SearchPageFragmentBinding
    lateinit var filterArrayList: ArrayList<DataManga>
    lateinit var orginalList: ArrayList<DataManga>
    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SearchPageFragmentBinding.inflate(layoutInflater)
        binding.searchRV.layoutManager = GridLayoutManager(context, 3)
        filterArrayList = ArrayList()
        orginalList = ArrayList()
        navController = findNavController()





            //for search in RecycleView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(p0: String?): Boolean {


                if (p0 != null) {

                    downViewModel.setSearch(p0)
                }
//                if (p0 != null) {
//                    if (p0.isNotBlank()) {
//                        filterArrayList.clear()
//                        val search = p0.lowercase(Locale.getDefault())
//
//                        orginalList.forEach {
//                            if (it.title.lowercase(Locale.getDefault()).contains(search)) {
//                                filterArrayList.add(it)
//                            }
//                            binding.searchRV.adapter = SearchAdapter(filterArrayList)
//                        }
//
//                    } else {
//                        filterArrayList.clear()
//                        binding.searchRV.adapter = SearchAdapter(orginalList)
//                    }
//
//                }
                return true
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downViewModel.mangaSearch().observe(
            viewLifecycleOwner, Observer {
                binding.searchRV.adapter = SearchAdapter(it)

                val sharedP = context?.getSharedPreferences("search",Context.MODE_PRIVATE)
                val saveSearch = sharedP?.edit()

                saveSearch?.apply(){
                    putString("search",it.toString())
                    apply()
                }


                Log.d(TAG, "from search $it")
            }
        )
    }

    private inner class SearchAdapter(val list: List<DataManga>) :
        RecyclerView.Adapter<SearchAdapter.SearchHolder>() {
        inner class SearchHolder(val binding: SearchItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(currentManga:DataManga){
                binding.imgSearch.load(currentManga.img)
                binding.titleSearch.setText(currentManga.title)
                binding.imgSearch.setOnClickListener {
                    val extras = FragmentNavigatorExtras(binding.imgSearch to "sec_img")
                    val action = downloadPageFragmentDirections.actionSearchPageFragmentToMangaPageDetailsFragment(currentManga)
                    navController.navigate(action,extras)
                }

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
            val binding = SearchItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return SearchHolder(binding)
        }

        override fun onBindViewHolder(holder: SearchHolder, position: Int) {

            val currentManga = list[position]
            holder.bind(currentManga )
//            with(holder) {
//                binding.imgSearch.load(query.img)
//                binding.titleSearch.setText(query.title)
//
//            }
        }

        override fun getItemCount(): Int = list.size
    }


}