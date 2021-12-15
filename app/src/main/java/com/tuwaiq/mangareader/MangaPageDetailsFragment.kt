package com.tuwaiq.mangareader

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tuwaiq.mangareader.databinding.MangaPageDetailsFragmentBinding

class MangaPageDetailsFragment : Fragment() {



    private  val pageDetailsViewModel: MangaPageDetailsViewModel by lazy { ViewModelProvider(this)[MangaPageDetailsViewModel::class.java] }
    private lateinit var binding:MangaPageDetailsFragmentBinding
    private lateinit var navController: NavController
//      lateinit var title_args:String
//   lateinit var last_ch_args:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        val titleArgs = args?.get("title")
        binding.nameTxtView.text = titleArgs.toString()
//        title_args = requireArguments().getString("title_args").toString()
//        last_ch_args =requireArguments().getString("last_ch_args").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MangaPageDetailsFragmentBinding.inflate(layoutInflater)
        navController = findNavController()

//        binding.nameTxtView.text = title_args
//        binding.lastChTxtView2.text= last_ch_args


        return  binding.root

    }



}