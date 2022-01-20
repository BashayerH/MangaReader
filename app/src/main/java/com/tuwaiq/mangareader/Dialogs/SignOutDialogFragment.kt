package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentSignOutDilogBinding


class SignOutDialogFragment :DialogFragment() {

    lateinit var binding:FragmentSignOutDilogBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var naveController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignOutDilogBinding.inflate(layoutInflater)
        naveController = findNavController()

        binding.NoBtn.setOnClickListener {
            dismiss()
        }
        binding.yesBtn.setOnClickListener {
            firebaseAuth.signOut()
            naveController.navigate(R.id.signPageFragment)
            dismiss()
        }

//        MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
//            .setTitle("Sign out!")
//            .setMessage("are you sure!!")
//
//            .setNegativeButton("no by mistake") { dialog, which ->
//
//                dialog.dismiss()
//            }
//            .setPositiveButton("yes i'am") { dialog, which ->
//                firebaseAuth.signOut()
//                naveController.navigate(R.id.signPageFragment)
//          dismiss()
//            }
//            .show()


        return binding.root
    }


}