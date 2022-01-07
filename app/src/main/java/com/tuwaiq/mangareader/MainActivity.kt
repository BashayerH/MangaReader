package com.tuwaiq.mangareader


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.mangareader.Dialogs.SignOutDialogFragment
import com.tuwaiq.mangareader.comments.CommentsPageFragment
import com.tuwaiq.mangareader.databinding.ActivityMainBinding
import com.tuwaiq.mangareader.downlodPage.downloadPageFragment
import com.tuwaiq.mangareader.homePage.MainPageFragment

import com.tuwaiq.mangareader.register.infoUserCollection

import java.net.URI
import java.util.concurrent.TimeUnit


//val firebaseStore = Firebase.firestore
private const val TAG = "MainActivity"
const val   Work_ID =   "1"

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id"
    private val notificationId = 0

    private val viewModelMain :MainViewModel by viewModels()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storgeImg = FirebaseStorage.getInstance().reference


    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var imgViewList: ImageView
    private lateinit var titleChange: TextView
    private lateinit var naveController: NavController

//    private  var chngePhoto:TextView = findViewById(R.id.changePhoto)
    private lateinit var imgUri: Uri
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var appBar: AppBarConfiguration
    private lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for splash screen
        installSplashScreen().apply {
            setKeepVisibleCondition{
                viewModelMain.isLoding.value
            }
        }

        //for notifgation

//        findViewById<Button>(R.id.addBtnComm).setOnClickListener {
//            sendNotif()
//        }




        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerNavLayout
        navigationView = binding.navDrawXml
        imgViewList = binding.imgViewActionBar
         bottomNav = binding.menuBottom



//        val currentNavigationFragment: Fragment?
//
//        supportFragmentManager.findFragmentById(R.id.fragmentNavContainerView)
//            ?.childFragmentManager
//            ?.fragments

        naveController = findNavController(R.id.fragmentNavContainerView)

//        chngePhoto= findViewById(R.id.changePhoto)

        //to connect img on app bar with drawer
        imgViewList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //to change img profile
//        chngePhoto.setOnClickListener {
//            // var dilog = SignOutDialogFragment()
//            //   dilog.show(supportFragmentManager, "signOutDialog")
//           // changeImg()
//        }
        //to change app bar title
        titleChange = binding.titleAppBar
        //to connect home img on app bar with home fragment


        //to show bottom navigation
        appBar = AppBarConfiguration(naveController.graph, drawerLayout)
        bottomNav.setupWithNavController(naveController)


        //to show navigation draw
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        NavigationUI.setupWithNavController(navigationView, naveController)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //to handle app bar
        navDistnation()
        drawerOnClick()
        showNotification()
     //   onMenuItemClick()


    }
//    private fun hideBottomAppBar() {
//        binding.run {
//            bottomNav.perf
//            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
//            // the fab and bottom app bar until after it's exit animation finishes.
//            bottomAppBar.animate().setListener(object : AnimatorListenerAdapter() {
//                var isCanceled = false
//                override fun onAnimationEnd(animation: Animator?) {
//                    if (isCanceled) return
//
//                    // Hide the BottomAppBar to avoid it showing above the keyboard
//                    // when composing a new email.
//                    bottomAppBar.visibility = View.GONE
//                    fab.visibility = View.INVISIBLE
//                }
//                override fun onAnimationCancel(animation: Animator?) {
//                    isCanceled = true
//                }
//            })
//        }
//    }




    private fun navDistnation() {
        naveController.addOnDestinationChangedListener { _, destnation, _ ->
            titleChange.text = destnation.label

            when (destnation.id) {
                R.id.mainPageFragment -> {
                    binding.appBar.visibility = View.VISIBLE
                    binding.menuBottom.visibility=View.VISIBLE
                }
                R.id.signPageFragment -> {
                    binding.appBar.visibility = View.GONE
                    //  binding.navDrawXml.visibility =View.GONE
                    binding.menuBottom.visibility=View.GONE
                }
                R.id.registerFragment -> {
                    binding.appBar.visibility = View.GONE
                    binding.menuBottom.visibility =View.GONE
                }
             //   R.id.downloadPageFragment ->
            }
        }
    }

    fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.downloadPageFragment ->findViewById<Button>(R.id.addBtnComm)

        }
        return true
    }

    private fun toSearch() {

        val exsitTran = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
//        val direction = downloadPageFragment

        naveController.navigate(R.id.downloadPageFragment)
    }

    private fun drawerOnClick() {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {

                   var dilog = SignOutDialogFragment()
                    dilog.show(supportFragmentManager, "signOutDialog")
//                    firebaseAuth.signOut()
//                    naveController.navigate(R.id.signPageFragment)
                    drawerLayout.close()

                    true
                }
                R.id.FavoriteFragment -> {
                    naveController.navigate(R.id.FavoriteFragment)
                    drawerLayout.close()
                    true
                }
                R.id.uploadMangaFragment -> {
                    naveController.navigate(R.id.uploadMangaFragment)
                    drawerLayout.close()
                    true
                }

                else -> true
            }
        }
        updateUserName()
    }

    private fun updateUserName() {
        val person = InfoUser()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            e(TAG, "updateUserName: ${currentUser.uid} " )
            infoUserCollection.document(currentUser.uid)
                .get()
                .addOnSuccessListener {
                    infoUserCollection.document(currentUser!!.uid).get()
                        .addOnSuccessListener {
                            val infoUserList = it.data
                            infoUserList?.forEach {
                                when (it.key) {
                                    "userName" -> findViewById<TextView>(R.id.profileName).setText(
                                        it.value.toString()
                                    )
                                    "emil" -> findViewById<TextView>(R.id.emilProfil).setText(it.value.toString())
                                }


                                Log.d(TAG, "DocumentSnapshot data: ${infoUserList}")
                            }
                        }
                }
        }
    }


    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.fragmentNavContainerView)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
//     fun creatNotif(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val name = "Notification Title"
//            val descriptionText = "description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
//                description = descriptionText
//            }
//            val notifManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notifManager.createNotificationChannel(channel)
//
//        }
//    }
//     fun sendNotif(){
//        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
//            .setContentTitle("test")
//            .setContentText("just for show")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        with(NotificationManagerCompat.from(this)){
//            notify(notificationId,builder.build())
//        }
//    }

     fun showNotification() {
         val constraints = Constraints.Builder()
             .setRequiredNetworkType(NetworkType.CONNECTED)
             .build()
         val workRequest = PeriodicWorkRequestBuilder<WorkManager>(15, TimeUnit.MINUTES)
             .setConstraints(constraints)
             .build()

         androidx.work.WorkManager.getInstance(this).enqueueUniquePeriodicWork(
             Work_ID,
             ExistingPeriodicWorkPolicy.KEEP,
             workRequest

         )
     }


//        val builder = this?.let {
//            NotificationCompat.Builder(it,"cahnnel_id")
//                .setContentTitle("test for comment")
//                .setContentText("some one comment on your comment")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//              //  .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//        }
//
//        with(NotificationManagerCompat.from(this)){
//            notify(0,builder!!.build())
//            creatFunChannel()
//        }
//
//    }
//
//     fun creatFunChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val name =getString(R.string.app_name)
//            val descriptionTxt = getString(R.string.app_name)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel("CHANNEL_ID",name,importance).apply {
//                description =descriptionTxt
//            }
//              val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//          notificationManager.createNotificationChannel(channel)
//
//
//
//
//        }
//    }


}