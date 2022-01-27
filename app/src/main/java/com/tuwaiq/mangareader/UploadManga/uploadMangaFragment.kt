package com.tuwaiq.mangareader.UploadManga

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.UploudItemFragmentBinding
import com.tuwaiq.mangareader.databinding.UploudPageFragmentBinding

private const val TAG = "uploadMangaFragment"

class UploadMangaFragment : Fragment() {


    private val uploadViewModel: UploadMangaViewModel by lazy { ViewModelProvider(this)[UploadMangaViewModel::class.java] }
    lateinit var binding:UploudPageFragmentBinding
     var mangaPdfUri :Uri? = null
    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UploudPageFragmentBinding.inflate(layoutInflater)

        navController = findNavController()



        binding.addPdf.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type= "application/pdf"


                startActivityForResult(it, Constants.REQUEST_PDF)

            }
            binding.pdfManga.setText(" PDF FILE")
            binding.pdfManga.setOnClickListener {
                navController.navigate(R.id.pdfViewFragment)
            }

        }


        return binding.root
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode == Constants.REQUEST_PDF ){
            if (data !=null){

                val mPdf = binding.pdfManga
                mangaPdfUri = data.data!!
                uploadViewModel.mangaPdf(mangaPdfUri!!)

                mPdf.setText(mangaPdfUri.toString())
            }
        }
    }



}