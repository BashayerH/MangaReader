package com.tuwaiq.mangareader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.databinding.ActivityMainBinding
import com.tuwaiq.mangareader.favorite.FavoriteFragment
import com.tuwaiq.mangareader.favorite.FavoriteViewModel
import com.tuwaiq.mangareader.homePage.MainPageFragment

import com.tuwaiq.mangareader.register.infoUserCollection


val firebaseStore = Firebase.firestore
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var imgViewList: ImageView
    private lateinit var titleChange: TextView
    private lateinit var naveController: NavController

    //rivate lateinit var bottomNav:BottomNavigationView
    private lateinit var appBar: AppBarConfiguration
    private lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerNavLayout
        navigationView = binding.navDrawXml
        imgViewList = binding.imgViewActionBar
        // bottomNav = binding.menuBottom

        naveController = findNavController(R.id.fragmentNavContainerView)


        //to connect img on app bar with drawer
        imgViewList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //to change app bar title
        titleChange = binding.titleAppBar
        //to connect home img on app bar with home fragment


        //to show bottom navigation
        appBar = AppBarConfiguration(naveController.graph, drawerLayout)


        //to show navigation draw
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        NavigationUI.setupWithNavController(navigationView, naveController)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //to handle app bar
        navDistnation()
        drawerOnClick()


    }

    private fun navDistnation() {
        naveController.addOnDestinationChangedListener { _, destnation, _ ->
            titleChange.text = destnation.label
            // profileName.text = destnation.arguments.toString()
            //   profileName.text = destnation.arguments.toString()
            when (destnation.id) {
                R.id.mainPageFragment -> {
                    binding.appBar.visibility = View.VISIBLE
                }
                R.id.signPageFragment -> {
                    binding.appBar.visibility = View.GONE
                    //  binding.navDrawXml.visibility =View.GONE
                }
                R.id.registerFragment -> {
                    binding.appBar.visibility = View.GONE
                }
            }
        }
    }

    private fun drawerOnClick() {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                   // var dilog = SignOutDialogFragment()
                 //   dilog.show(supportFragmentManager, "signOutDialog")
                    firebaseAuth.signOut()
                    naveController.navigate(R.id.action_mainPageFragment_to_signPageFragment)
                    drawerLayout.close()

                    true
                }
                R.id.favoriteFragment -> {
                    naveController.navigate(R.id.favoriteFragment)
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

}