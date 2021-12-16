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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.databinding.ActivityMainBinding
import com.tuwaiq.mangareader.favorite.FavoriteFragment
import com.tuwaiq.mangareader.favorite.FavoriteViewModel
import com.tuwaiq.mangareader.homePage.MainPageFragment

import com.tuwaiq.mangareader.register.infoUserCollection


val firebaseAuth: FirebaseAuth  = FirebaseAuth.getInstance()
    val firebaseStore = Firebase.firestore
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


private lateinit var binding: ActivityMainBinding
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigationView:NavigationView
private lateinit var imgViewList:ImageView
private lateinit var homeBtn:ImageView
private lateinit var titleChange:TextView
private lateinit var naveController: NavController
private lateinit var bottomNav:BottomNavigationView
private lateinit var appBar:AppBarConfiguration




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

         drawerLayout = binding.drawerNavLayout
         navigationView= binding.navDrawXml
         imgViewList = binding.imgViewActionBar
        bottomNav = binding.menuBottom



        naveController = findNavController(R.id.fragmentNavContainerView)

            //to connect img on app bar with drawer
        imgViewList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
            //to change app bar title
        titleChange = binding.titleAppBar
           //to connect home img on app bar with home fragment



        //to show bottom navigation
        bottomNav.setupWithNavController(naveController)
        appBar= AppBarConfiguration(naveController.graph,drawerLayout)

        //to show navigation draw
        NavigationUI.setupWithNavController(navigationView,naveController)
         //to handle app bar
        navDistnation()
        drawerOnClick()


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
        updateUserName()
    }

    private fun updateUserName() {
        val currentUser = firebaseAuth.currentUser
        infoUserCollection.document(currentUser!!.uid).get()
            .addOnSuccessListener {
                val infoUserList = it.data
                infoUserList?.forEach {
                    when(it.key){
                        "userName" -> findViewById<TextView>(R.id.profileName).setText(it.value.toString())
                        "emil" -> findViewById<TextView>(R.id.emilProfil).setText(it.value.toString())

                    }

                }
            }


    }



    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.fragmentNavContainerView)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

}