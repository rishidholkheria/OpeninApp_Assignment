package com.example.openinapp.ui.links

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.openinapp.api.LinksAPI
import com.example.openinapp.api.RetrofitHelper
import com.example.openinapp.databinding.FragmentLinksBinding
import com.example.openinapp.repository.LinksRepository

class Links : Fragment() {

    private var _binding : FragmentLinksBinding ?= null
    private val binding get() = _binding!!

    lateinit var linksViewModel : LinksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linkService = RetrofitHelper.getInstance().create(LinksAPI::class.java)
        val repo = LinksRepository(linkService)

        linksViewModel = ViewModelProvider(this,LinkViewModelFactory(repo)).get(LinksViewModel::class.java)

        linksViewModel.linkLiveData.observe(viewLifecycleOwner) {
            Log.d("Response Data", it.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}