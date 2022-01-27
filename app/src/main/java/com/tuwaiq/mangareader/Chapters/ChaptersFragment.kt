package com.tuwaiq.mangareader.Chapters

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.ChapterItemBinding
import com.tuwaiq.mangareader.databinding.ChaptersFragmentBinding

import com.tuwaiq.mangareader.mangaApi.models.Chapter

class ChaptersFragment : Fragment() {



    private lateinit var binding: ChaptersFragmentBinding
    private val navArgs by navArgs <ChaptersFragmentArgs>()
    private val chapterViewModel: ChaptersViewModel by lazy { ViewModelProvider(this)[ChaptersViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChaptersFragmentBinding.inflate(layoutInflater)
        binding.chapterRV.layoutManager = LinearLayoutManager(context)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentManga = navArgs.currentManga!!.id
        chapterViewModel.detailsData(currentManga).observe(
            viewLifecycleOwner){
            it.forEach {itlist ->
                binding.chapterRV.adapter = ChapterAdapter(itlist.data.chapters)
            }
        }
    }


    private inner class ChapterAdapter(val list: List<Chapter>): RecyclerView.Adapter<ChapterAdapter.ChapterHolder>(){
        inner class ChapterHolder(val binding: ChapterItemBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
            val binding = ChapterItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return ChapterHolder(binding)
        }

        override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
            val ch: Chapter =list[position]
            with(holder){

                binding.chTitle.setText(ch.title)
                binding.uploadAt.setText(ch.uploaded_at)
                binding.url.setOnClickListener {
                    gotUrl(ch.url)
                }

            }

        }

        override fun getItemCount(): Int= list.size
    }
    private fun gotUrl(s: String) {
        val uri: Uri = Uri.parse(s)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }


}