package com.tuwaiq.mangareader.downlodPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.DownloadPageFragmentBinding
import com.tuwaiq.mangareader.databinding.SearchItemBinding
import com.tuwaiq.mangareader.favorite.FavoriteFragment
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "downloadPageFragment"

class downloadPageFragment : Fragment() {


    private val downViewModel: DownloadPageViewModel by lazy { ViewModelProvider(this)[DownloadPageViewModel::class.java] }
    lateinit var binding: DownloadPageFragmentBinding
    lateinit var filterArrayList: ArrayList<DataManga>
    lateinit var orginalList: ArrayList<DataManga>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DownloadPageFragmentBinding.inflate(layoutInflater)
        binding.searchRV.layoutManager = GridLayoutManager(context, 2)
        filterArrayList = ArrayList()
        orginalList = ArrayList()

            //for search in RecycleView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {

                    downViewModel.setSearch(p0)
                }
                return true

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isNotBlank()) {
                        filterArrayList.clear()
                        val search = p0.lowercase(Locale.getDefault())

                        orginalList.forEach {
                            if (it.title.lowercase(Locale.getDefault()).contains(search)) {
                                filterArrayList.add(it)
                            }
                            binding.searchRV.adapter = SearchAdapter(filterArrayList)
                        }

                    } else {
                        filterArrayList.clear()
                        binding.searchRV.adapter = SearchAdapter(orginalList)
                    }

                }
                return false
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downViewModel.mangaSearch().observe(
            viewLifecycleOwner, Observer {
                binding.searchRV.adapter = SearchAdapter(it)

                Log.d(TAG, "from search $it")
            }
        )
    }

    private inner class SearchAdapter(val list: List<DataManga>) :
        RecyclerView.Adapter<SearchAdapter.SearchHloder>() {
        inner class SearchHloder(val binding: SearchItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHloder {
            val binding = SearchItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return SearchHloder(binding)
        }

        override fun onBindViewHolder(holder: SearchHloder, position: Int) {
            val query = list[position]
            with(holder) {
                binding.imgSearch.load(query.img)
                binding.titleSearch.setText(query.title)
            }
        }

        override fun getItemCount(): Int = list.size
    }


}