package com.tuwaiq.mangareader.changePhoto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.ChangePhotoFragmentBinding

class ChangePhotoFragment : Fragment() {


    private val photoViewModel: ChangePhotoViewModel by lazy { ViewModelProvider(this)[ChangePhotoViewModel::class.java] }

    private lateinit var binding:ChangePhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_photo_fragment, container, false)
    }



}