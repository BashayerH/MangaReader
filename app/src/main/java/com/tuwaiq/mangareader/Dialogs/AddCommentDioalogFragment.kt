package com.tuwaiq.mangareader.Dialogs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.MainActivity
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.comments.CommentsPageFragment
import com.tuwaiq.mangareader.comments.CommentsPageFragmentArgs
import com.tuwaiq.mangareader.databinding.FragmentAddCommentDioalogBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga

private const val TAG = "AddCommentDioalogFragme"
class AddCommentDioalogFragment : DialogFragment() {

    private val CHANNEL_ID = "channel_id"
    private val notification = 0

    val main :MainActivity = MainActivity()
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
        binding.addBtnComm.setOnClickListener {
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
            main.showNotification()

                 }
        return binding.root
    }




}