package com.tuwaiq.mangareader.UploadManga

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.PHOTO
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.UploudItemFragmentBinding
import com.tuwaiq.mangareader.databinding.UploudPageFragmentBinding
import com.tuwaiq.mangareader.modelsCatg.CatgMangData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URI

private const val TAG = "uploadMangaFragment"
private const val REQUEST_PDF =0
class UploadMangaFragment : Fragment() {


    private val uploadViewModel: UploadMangaViewModel by lazy { ViewModelProvider(this)[UploadMangaViewModel::class.java] }
    lateinit var binding:UploudPageFragmentBinding
     var mangaPdfUri :Uri? = null
    var userInfo:InfoUser = InfoUser()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var infoUserCollection = Firebase.firestore.collection("InfoUser")
    val firebaseUser= Firebase.auth.currentUser
    var mangaUpRef = Firebase.storage.reference.child("/pdfManga/${firebaseAuth.currentUser?.uid}")
    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UploudPageFragmentBinding.inflate(layoutInflater)
        binding.catgRV.layoutManager = LinearLayoutManager(context)
        navController = findNavController()



        binding.addPdf.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type= "application/pdf"


                startActivityForResult(it, REQUEST_PDF)

            }
            binding.pdfManga.setOnClickListener {
                navController.navigate(R.id.pdfViewFragment)
            }
        }


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
                    navController.navigate(R.id.pdfViewFragment)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode == REQUEST_PDF ){
            if (data !=null){
                val mPdf = binding.pdfManga
                mangaPdfUri = data.data!!
                uploadViewModel.mangaPdf(mangaPdfUri!!)
                mPdf.setText(mangaPdfUri.toString())
            }
        }
    }



}