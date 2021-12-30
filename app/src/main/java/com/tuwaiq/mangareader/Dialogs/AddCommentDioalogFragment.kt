package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.comments.CommentsPageFragmentArgs
import com.tuwaiq.mangareader.databinding.FragmentAddCommentDioalogBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga

private const val TAG = "AddCommentDioalogFragme"
class AddCommentDioalogFragment : DialogFragment() {

    private lateinit var binding:FragmentAddCommentDioalogBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser!!.uid
    var commentCollection = Firebase.firestore.collection("CommentManga")
   private val navArgs by navArgs <CommentsPageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentAddCommentDioalogBinding.inflate(layoutInflater)
        binding.addBtn.setOnClickListener {
            val manga=DataManga()
        val comment=CommentData(userEmail = firebaseAuth.currentUser!!.email.toString(),userId = firebaseAuth.currentUser!!.uid
            ,msg = binding.addCommentTxt.text.toString(),mangaId =navArgs.currentManga!!.id,time = Timestamp.now())

            val coomId = commentCollection.document().id
      commentCollection.document(coomId).set(comment)
                        .addOnSuccessListener {
                  //  Toast.makeText(context,"added to comment",Toast.LENGTH_LONG).show()
                  Log.d(TAG,"fav manga list $commentCollection")
                }
            dismiss()


                 }
        return binding.root
    }


}