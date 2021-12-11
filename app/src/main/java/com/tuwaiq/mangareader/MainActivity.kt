package com.tuwaiq.mangareader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.tuwaiq.mangareader.databinding.ActivityMainBinding
import com.tuwaiq.mangareader.homePage.MainPageFragmentArgs

class MainActivity : AppCompatActivity() {


private lateinit var binding: ActivityMainBinding
private lateinit var drawerLayout: DrawerLayout

private lateinit var navigationView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

         drawerLayout = binding.drawerNavLayout
         navigationView= binding.navDrawXml

        val navController = Navigation.findNavController(this,R.id.fragmentNavContainerView)
           findNavController(R.id.fragmentNavContainerView)
      //var   appBarConfgration = AppBarConfiguration(navController.graph,drawerLayout)

            //to show burger bar
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
            //to show navigation draw
        NavigationUI.setupWithNavController(navigationView,navController)
//try to disply name in profile
//        val args = MainPageFragmentArgs by navArgs()
//        binding.
//        var nameTextView :TextView? = null
//           nameTextView = findViewById(R.id.profileName)
//        val viewName = nameTextView.text.toString()
//
//        updateUserName(userName)
    }

    private fun updateUserName(userName: String) {

        val headerView = navigationView.getHeaderView(0)
        val viewUserName =headerView.findViewById<TextView>(R.id.profileName)
        viewUserName.text =userName
    }



    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.fragmentNavContainerView)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

}