package com.tuwaiq.mangareader.downlodPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuwaiq.mangareader.R

class downloadPageFragment : Fragment() {

    companion object {
        fun newInstance() = downloadPageFragment()
    }

    private lateinit var viewModel: DownloadPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.download_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DownloadPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}