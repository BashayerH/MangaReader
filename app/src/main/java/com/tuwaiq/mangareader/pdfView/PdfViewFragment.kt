package com.tuwaiq.mangareader.pdfView

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentSenstiveDialogBinding
import com.tuwaiq.mangareader.databinding.PdfViewFragmentBinding

private const val TAG = "PdfViewFragment"
class PdfViewFragment : Fragment() {



    private val pdfViewModel: PdfViewViewModel by lazy { ViewModelProvider(this)[PdfViewViewModel::class.java] }

    lateinit var binding: PdfViewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PdfViewFragmentBinding.inflate(layoutInflater)


        loadPdf()
        return binding.root
    }

    private fun getPdfFile(mangaUrl:String){
        var getRef = Firebase.storage.getReferenceFromUrl(mangaUrl)
        getRef.getBytes(100000000)
            .addOnSuccessListener {
                binding.pdfView.fromBytes(it)
                    .swipeHorizontal(false)
                    .onPageChange{page,Pcount ->
                        val pages = page+1
                        Log.d(TAG, "manga from url:$pages ")
                    }.onError {
                        Log.d(TAG, "manga from url with error: $it ")
                        Toast.makeText(context,"something wrong in pdf ",Toast.LENGTH_LONG).show()
                    }.onPageError { page, t ->
                        Log.d(TAG, "manga page from url with error: $t ,${t.message}")
                        Toast.makeText(context,"something wrong in page pdf ",Toast.LENGTH_LONG).show()

                    }.load()
            }
    }

    private fun loadPdf(){
        var mangaPdfUri : Uri? = null
      Constants.infoUserCollection.document(Constants.firebaseAuth.currentUser!!.uid).get()
            .addOnSuccessListener {
             val  mangaUrl = it.get("uplodedFile")
                getPdfFile(mangaUrl as String)
            }
    }

}