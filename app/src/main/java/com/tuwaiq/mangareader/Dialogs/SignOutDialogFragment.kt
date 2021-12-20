package com.tuwaiq.mangareader.Dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentSignOutDilogBinding


class SignOutDialogFragment :DialogFragment() {

    lateinit var binding:FragmentSignOutDilogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignOutDilogBinding.inflate(layoutInflater)
        return binding.root
    }


}