package com.tuwaiq.mangareader.comments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.Dialogs.AddCommentDioalogFragment
import com.tuwaiq.mangareader.MainActivity
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.CommentItemBinding
import com.tuwaiq.mangareader.databinding.CommentsPageFragmentBinding
import com.tuwaiq.mangareader.databinding.FavoritItemBinding
import com.tuwaiq.mangareader.mangaPageDetails.MangaPageDetailsFragmentArgs

class CommentsPageFragment : Fragment() {





    private val commentViewModel: CommentsPageViewModel by lazy { ViewModelProvider(this)[CommentsPageViewModel::class.java] }
    private lateinit var binding:CommentsPageFragmentBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val navArgs by navArgs <CommentsPageFragmentArgs>()
    val firebaseUser = firebaseAuth.currentUser!!.uid
    var commentCollection = Firebase.firestore.collection("CommentManga")
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CommentsPageFragmentBinding.inflate(layoutInflater)
        binding.recyclerViewComment.layoutManager = LinearLayoutManager(context)
        navController = findNavController()

        binding.refreshComm.setOnRefreshListener {
            if (binding.refreshComm.isRefreshing) {
                binding.refreshComm.isRefreshing = false
            }
            commentViewModel.getComments(commentCollection.document().id)
           // loadData(currentUser!!.uid)
        }
        binding.addComment.setOnClickListener {

          //  val dioalog = AddCommentDioalogFragment()
           // dioalog.navArgs<CommentsPageFragmentArgs>()
          //  navArgs.currentManga!!.id
          //  fragmentManager?.let { it1 -> dioalog.show(it1,"add comment") }
            val action = CommentsPageFragmentDirections.actionCommentsPageFragmentToAddCommentDioalogFragment(navArgs.currentManga)
           navController.navigate(action)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentViewModel.getComments(commentCollection.document().id).observe(
            viewLifecycleOwner, Observer {
                binding.recyclerViewComment.adapter=CommentAdapter(it)
            }
        )
    }


    private inner class CommentAdapter(val list: List<CommentData>):RecyclerView.Adapter<CommentAdapter.CommentHolder>(){
            inner class CommentHolder(val binding: CommentItemBinding):RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
            val binding = CommentItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return CommentHolder(binding)
        }

        override fun onBindViewHolder(holder: CommentHolder, position: Int) {
            val com:CommentData =list[position]
            with(holder){
                if (navArgs.currentManga!!.id == com.mangaId){
                    binding.userName.setText(com.userEmail)
                    binding.commentDecs.setText(com.msg)
                    binding.time.setText(com.time.toDate().toString())
                    binding.commentItem.visibility = View.VISIBLE
                }else{
                    //binding.commentItem.visibility = View.INVISIBLE
                        //دا الكود يخلي الايتم مخفي
                   // binding.commentItem.removeView(View(context))
                    binding.commentItem.removeAllViewsInLayout()

                }

            }

        }

        override fun getItemCount(): Int= list.size
    }
    private fun showNotification() {
        val  intent = Intent(context, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,0,intent,0
        )
        val builder = context?.let {
            NotificationCompat.Builder(it,"cahnnel_id")
                .setContentTitle("test for comment")
                .setContentText("some one comment on your comment")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
        }

        with(NotificationManagerCompat.from(requireContext())){
            notify(0,builder!!.build())
            creatFunChannel()
        }

    }

    private fun creatFunChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name =getString(R.string.app_name)
            val descriptionTxt = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID",name,importance).apply {
                description =descriptionTxt
            }
           //  val notificationManager:NotificationManager =




        }
    }





}