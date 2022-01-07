package com.tuwaiq.mangareader.UploadManga

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.UploudItemFragmentBinding
import com.tuwaiq.mangareader.databinding.UploudPageFragmentBinding
import com.tuwaiq.mangareader.modelsCatg.CatgMangData


class UploadMangaFragment : Fragment() {


    private val uploadViewModel: UploadMangaViewModel by lazy { ViewModelProvider(this)[UploadMangaViewModel::class.java] }
    lateinit var binding:UploudPageFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UploudPageFragmentBinding.inflate(layoutInflater)
        binding.catgRV.layoutManager = LinearLayoutManager(context)

        return binding.root
    }




    private inner class CatgMangaAdabter( val listCatg:List<CatgMangData> ):RecyclerView.Adapter<CatgMangaAdabter.CatgMangaHolder>(){
         inner class CatgMangaHolder(val binding:UploudItemFragmentBinding):RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatgMangaHolder {
            val binding = UploudItemFragmentBinding.inflate(
                layoutInflater,parent,false)
            return CatgMangaHolder(binding)

        }

        override fun onBindViewHolder(holder: CatgMangaHolder, position: Int) {
            val catgManga = listCatg[position]
            with(holder){
                binding.titleCatg.setText(catgManga.title)
                binding.imageCatg.load(catgManga.url)
                when(catgManga.title){
                    "Action"   -> binding.itemView.visibility
                    "Comedy" -> binding.itemView.visibility = View.VISIBLE
                    "Romance" -> binding.itemView.visibility = View.VISIBLE
                    "School Life" -> binding.itemView.visibility = View.VISIBLE
                    "Fantasy" -> binding.itemView.visibility = View.VISIBLE
                    "Adventure" -> binding.itemView.visibility = View.VISIBLE
                    else -> binding.itemView.removeAllViewsInLayout()
                }

                binding.titleCatg.setOnClickListener {
                   // val action = UploadMangaFragmentDirections.actionUploadMangaFragmentToReadWebVeiwFragment(currentManga =)
                }
            }
        }

        override fun getItemCount(): Int = listCatg.size
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadViewModel.getCatg.observe(
            viewLifecycleOwner, Observer {
                binding.catgRV.adapter = CatgMangaAdabter(it)
            }
        )
    }




}