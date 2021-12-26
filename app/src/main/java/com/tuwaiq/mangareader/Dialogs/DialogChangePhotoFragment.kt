package com.tuwaiq.mangareader.Dialogs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.FragmentDialogChangePhotoBinding


class DialogChangePhotoFragment : Fragment() {
    private lateinit var binding:FragmentDialogChangePhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDialogChangePhotoBinding.inflate(layoutInflater)

       return binding.root

    }
//    private fun changeImg() {
//        var intent= Intent()
//        intent.type= "images/*"
//        intent.action= Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent,"Take photo"),100)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        var imgProfil: ImageView = findViewById(R.id.navHeaderImg)
//        if (requestCode==100 && resultCode== AppCompatActivity.RESULT_OK){
//            imgUri = data?.data!!
//            imgProfil!!.setImageURI(imgUri)
//
//        }
//
//    }


}