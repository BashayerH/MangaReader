package com.tuwaiq.mangareader.Dialogs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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
import com.sdsmdg.harjot.vectormaster.utilities.Utils
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.MainActivity
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.comments.CommentData
import com.tuwaiq.mangareader.comments.CommentsPageFragment
import com.tuwaiq.mangareader.comments.CommentsPageFragmentArgs
import com.tuwaiq.mangareader.databinding.FragmentAddCommentDioalogBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

private const val TAG = "AddCommentDioalogFragme"
class AddCommentDioalogFragment : DialogFragment() {


    private lateinit var binding:FragmentAddCommentDioalogBinding
    val firebaseUser = Constants.firebaseAuth.currentUser!!.uid
   private val navArgs by navArgs <CommentsPageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creatFunChannel()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        UIUtil.hideKeyboard(context,binding.addCommentTxt)
       // UIUtil.showKeyboard(context,binding.addCommentTxt)
        binding= FragmentAddCommentDioalogBinding.inflate(layoutInflater)
        binding.addBtnComm.setOnClickListener {
            val manga=DataManga()
        val comment=CommentData(userEmail = Constants.firebaseAuth.currentUser!!.email.toString(),userId = firebaseUser
            ,msg = binding.addCommentTxt.text.toString(),mangaId =navArgs.currentManga!!.id,time = Timestamp.now(),imgP = "")


            val coomId = Constants.commentCollection.document().id
     Constants.commentCollection.document(coomId).set(comment)
                        .addOnSuccessListener {
                  //  Toast.makeText(context,"added to comment",Toast.LENGTH_LONG).show()
                  Log.d(TAG,"fav manga list ${Constants.commentCollection}")
                }
            dismiss()
            showNotification()


                 }
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun showNotification() {
        val  intent = Intent(context, navArgs.currentManga?.title.toString()::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,0,intent,0
        )
        val builder = context?.let {
            NotificationCompat.Builder(it,"101")
                .setContentTitle("Manga comment page")
                .setContentText("some one comment on (${navArgs.currentManga?.title}), check that!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_m)
        }

        with(NotificationManagerCompat.from(requireContext())){
            notify(101,builder!!.build())

        }

    }

    private fun creatFunChannel(){
        val name =getString(R.string.app_name)
        val descriptionTxt = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("101",name,importance).apply {
            description =descriptionTxt
        }
        val notificationManager:NotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }




}