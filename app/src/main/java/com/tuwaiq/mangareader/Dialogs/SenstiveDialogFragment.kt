package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentSenstiveDialogBinding



class SenstiveDialogFragment : DialogFragment() {


    lateinit var binding: FragmentSenstiveDialogBinding
    private lateinit var naveController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSenstiveDialogBinding.inflate(layoutInflater)
        naveController = findNavController()

        binding.yesBtn.setOnClickListener {
            naveController.navigate(R.id.mainPageFragment)
            dismiss()
        }

        return binding.root
    }


}