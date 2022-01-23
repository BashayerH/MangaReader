package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.comments.CommentsPageFragmentArgs
import com.tuwaiq.mangareader.databinding.FragmentAddCommentDioalogBinding
import com.tuwaiq.mangareader.databinding.FragmentDescrDialogBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import com.tuwaiq.mangareader.mangaPageDetails.MangaPageDetailsFragmentArgs
import com.tuwaiq.mangareader.mangaPageDetails.MangaPageDetailsViewModel

private const val TAG = "DescrDialogFragment"
class DescrDialogFragment :DialogFragment() {


    private lateinit var binding:FragmentDescrDialogBinding
    private val dilogViewModel: DescrDialogVM by lazy { ViewModelProvider(this)[DescrDialogVM::class.java] }
    private val navArgs by navArgs <DescrDialogFragmentArgs>()
    private val navD by navArgs<DescrDialogFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDescrDialogBinding.inflate(layoutInflater)

            val currentManga = navArgs.currentManga!!.id
        dilogViewModel.detailsData(currentManga).observe(
            viewLifecycleOwner) { list ->
            list.forEach{
                    if (it.data.description.isEmpty()){
                        binding.descTxtView.text = navD.currentManga!!.description
                    }
                    else{
                        binding.descTxtView.text = it.data.description

                    }

                }
            }



        binding.buttonDone.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


}