package com.tuwaiq.mangareader.UploadManga

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.UploadMangaFragmentBinding
import com.tuwaiq.mangareader.firebaseAuth

class UploadMangaFragment : Fragment() {


    private val uploadViewModel: UploadMangaViewModel by lazy { ViewModelProvider(this)[UploadMangaViewModel::class.java] }
    private lateinit var binding :UploadMangaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UploadMangaFragmentBinding.inflate(layoutInflater)

        return binding.root
    }



}