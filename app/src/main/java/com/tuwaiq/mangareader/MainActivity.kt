package com.tuwaiq.mangareader


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.work.*
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.mangareader.Dialogs.SignOutDialogFragment
import com.tuwaiq.mangareader.databinding.ActivityMainBinding

import com.tuwaiq.mangareader.register.infoUserCollection
import java.lang.Exception
import java.util.*

import java.util.concurrent.TimeUnit


//val firebaseStore = Firebase.firestore
private const val TAG = "MainActivity"
const val   Work_ID =   "1"
const val KEY_LANG="key_lang"
const val PHOTO =0

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


    private lateinit var imgUri: Uri
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var appBar: AppBarConfiguration
    private lateinit var drawerToggle: ActionBarDrawerToggle




    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocate()
        super.onCreate(savedInstanceState)
        // for splash screen
        installSplashScreen().apply {
            setKeepVisibleCondition{
                viewModelMain.isLoding.value
            }
        }




        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerNavLayout
        navigationView = binding.navDrawXml
        imgViewList = binding.imgViewActionBar
         bottomNav = binding.menuBottom



        naveController = findNavController(R.id.fragmentNavContainerView)

        //to change img profile
//        val v =  findViewById<TextView>(R.id.changePhoto).setOnClickListener {
//            var dilog = DialogChangePhotoFragment()
//            dilog.show(supportFragmentManager, "PhotoDialog")
//        }

        //to connect img on app bar with drawer
        imgViewList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


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
    private fun hideBottomAppBar() {
        binding.run {

            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
            // the fab and bottom app bar until after it's exit animation finishes.
            bottomNav.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator?) {
                    if (isCanceled) return
                    // Hide the BottomAppBar to avoid it showing above the keyboard
                    // when composing a new email.
                    bottomNav.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator?) {
                    isCanceled = true
                }
            })
        }
    }





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
                R.id.searchPageFragment ->{
                   //a binding.menuBottom.visibility =View.GONE
                    hideBottomAppBar()
                }

            }
        }
    }



    private fun toSearch() {

        val exsitTran = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
//        val direction = downloadPageFragment

        naveController.navigate(R.id.searchPageFragment)
    }

    private fun drawerOnClick() {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {

                   var dilog = SignOutDialogFragment()
                    dilog.show(supportFragmentManager, "signOutDialog")

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
                R.id.changePhoto ->{
                    toChangePhoto()
                    true
                }

                R.id.lang -> {
                    val langList = arrayOf("English","عربي")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("change the language!")

                    builder.setSingleChoiceItems(langList,-1) { dialog, lang ->
                        if (lang ==0){
                            setLocate("en")
                          //  recreate()
                        }
                        else if (lang ==1){
                            setLocate("ar")
                           // recreate()
                        }
                        dialog.dismiss()
                    }
                    val creatD = builder.create()
                    creatD.show()

                    true
                }

                else -> true
            }
        }
        updateUserName()
    }

    private fun toChangePhoto() {


        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO)
    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PHOTO && resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val imgP = findViewById<ImageView>(R.id.navHeaderImg)
                    val  imageUri:Uri = data.data!!
                    try {
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                           contentResolver, imageUri
                        )
                        imgP.setImageBitmap(bitmap)
                        viewModelMain.uploadPhoto(imageUri)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
          return
        }



    private fun setLocate(lang: String) {
        val local =Locale(lang)
        Locale.setDefault(local)
        val config = Configuration()
        config.locale =local
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)

        getDefaultSharedPreferences(baseContext).edit()

        .putString("language",lang)
            .apply()
    }
    private fun loadLocate(){

        val sharedP=  getDefaultSharedPreferences(baseContext)
        val language =sharedP.getString(KEY_LANG,"")
        if (language != null) {
            setLocate(language)
        }
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
                                    "imgProfile" -> findViewById<ImageView>(R.id.navHeaderImg).load(it.value.toString())

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





}