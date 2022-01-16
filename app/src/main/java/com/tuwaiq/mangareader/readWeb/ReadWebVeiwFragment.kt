package com.tuwaiq.mangareader.readWeb

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.view.get
import androidx.navigation.fragment.navArgs
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.ReadWebVeiwFragmentBinding

class ReadWebVeiwFragment : Fragment() {


    lateinit var binding:ReadWebVeiwFragmentBinding
  private lateinit  var webView:WebView

    private val navArgs by navArgs<ReadWebVeiwFragmentArgs>()

    private lateinit var webViewModel: ReadWebVeiwViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= ReadWebVeiwFragmentBinding.inflate(layoutInflater)
     // webView= context?.let { WebView(it) }!!

        val currentUrl = navArgs.currentManga
       // val url = binding.webView.get[currentUrl.latest_chapter_title]
        val url = navArgs.currentManga!!.latest_chapter_url

      //  binding.progressBar.max=100
        webView = WebView(requireContext())
//
//      webView.webChromeClient = object :WebChromeClient(){
//          override fun onProgressChanged(view: WebView?, newProgress: Int) {
//              super.onProgressChanged(view, newProgress)
//              if (newProgress==100){
//                  binding.progressBar.visibility= View.GONE
//              }else{
//                  binding.progressBar.visibility =View.VISIBLE
//              }
//
//          }
//       }
        webView.settings.javaScriptEnabled =true
        webView.loadUrl(url)
//        webView.setPictureListener { webView, picture ->
//            if (picture !=null){
//                try {
////                    bmp = pictureDrawable2Bitmap(new PictureDrawable(
////                            picture))
//
//                }catch (Exption){
//                Exption
//                }
//        }
//        }


        return binding.root


    }



}