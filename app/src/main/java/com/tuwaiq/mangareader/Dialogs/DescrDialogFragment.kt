package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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

private const val TAG = "DescrDialogFragment"
class DescrDialogFragment :DialogFragment() {


    private lateinit var binding:FragmentDescrDialogBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser!!.uid
    var commentCollection = Firebase.firestore.collection("CommentManga")
    private val navArgs by navArgs <DescrDialogFragmentArgs>()
   // private val navD by navArgs<MangaPageDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDescrDialogBinding.inflate(layoutInflater)


            val currentManga = navArgs.currentManga!!.description
                val desc = navArgs.desc



            if (currentManga != null){
                binding.descTxtView.setText(currentManga)
            }else  {
                binding.descTxtView.setText(desc)
            }



        binding.buttonDone.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


}