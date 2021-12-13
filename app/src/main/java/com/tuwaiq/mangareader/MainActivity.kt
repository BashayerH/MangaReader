package com.tuwaiq.mangareader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.mangareader.databinding.ActivityMainBinding
import com.tuwaiq.mangareader.favorite.FavoriteFragment
import com.tuwaiq.mangareader.favorite.FavoriteViewModel
import com.tuwaiq.mangareader.homePage.MainPageFragment
import com.tuwaiq.mangareader.homePage.MainPageFragmentArgs


  val firebaseAuth: FirebaseAuth  = FirebaseAuth.getInstance()

class MainActivity : AppCompatActivity() {


private lateinit var binding: ActivityMainBinding
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigationView:NavigationView
private lateinit var imgViewList:ImageView
private lateinit var titleChange:TextView
private lateinit var naveController: NavController
//private lateinit var profileName:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

         drawerLayout = binding.drawerNavLayout
         navigationView= binding.navDrawXml
         imgViewList = binding.imgViewActionBar

        naveController = findNavController(R.id.fragmentNavContainerView)

            //to connect img on app bar with drawer
        imgViewList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
            //to change app bar title
        titleChange = binding.titleAppBar


            //try to disply name in profile
        // profileName = findViewById(R.id.profileName)
        //    profileName = binding.appBar


        //to show navigation draw
        NavigationUI.setupWithNavController(navigationView,naveController)
         //to handle app bar
        navDistnation()

        drawerOnClick()
            //to show burger bar but there issue
      //  NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

//try to disply name in profile
//        val args = MainPageFragmentArgs by navArgs()
//        binding.
//        var nameTextView :TextView? = null
//           nameTextView = findViewById(R.id.profileName)
//        val viewName = nameTextView.text.toString()
//
//        updateUserName(userName)
    }

    private fun navDistnation() {
         naveController.addOnDestinationChangedListener{_,destnation,_ ->
             titleChange.text = destnation.label
            // profileName.text = destnation.arguments.toString()
          //   profileName.text = destnation.arguments.toString()
             when(destnation.id){
                 R.id.mainPageFragment -> {
                     binding.appBar.visibility =View.VISIBLE
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
             when(it.itemId){
                 R.id.logout ->{
                            //FirebaseAuth.getInstance().signOut()
                        firebaseAuth.signOut()
                     naveController.navigate(R.id.action_mainPageFragment_to_signPageFragment)
                     drawerLayout.close()

                     true
                 }
                 R.id.favoriteFragment ->{
                     naveController.navigate(R.id.favoriteFragment)
                     drawerLayout.close()
                     true
                 }
                 R.id.uploadMangaFragment ->{
                     naveController.navigate(R.id.uploadMangaFragment)
                     drawerLayout.close()
                     true
                 }

                 else -> true
             }
         }
    }

    private fun updateUserName(userName: String) {

        val headerView = navigationView.getHeaderView(0)
        val viewUserName =headerView.findViewById<TextView>(R.id.profileName)
        viewUserName?.text = userName
    }



    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.fragmentNavContainerView)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

}