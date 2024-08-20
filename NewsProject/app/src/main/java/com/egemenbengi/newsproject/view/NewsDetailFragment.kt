package com.egemenbengi.newsproject.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.egemenbengi.newsproject.R
import com.egemenbengi.newsproject.databinding.FragmentNewsDetailBinding
import com.egemenbengi.newsproject.viewmodel.NewsDetailViewModel

class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    private var uuid = 0
    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]

        arguments?.let {
            uuid = NewsDetailFragmentArgs.fromBundle(it).id
        }
        viewModel.getLiveData(uuid)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.news.observe(viewLifecycleOwner){ news->
            binding.textNameID.text = news.name
            binding.textSourceID.text = news.source
            binding.textDescriptionID.text = news.description
            binding.btnGoToWepSiteID.setOnClickListener { goToWebSite(it, news.url) }
        }
    }

    private fun goToWebSite(view: View, newsUrl: String?){
        if (newsUrl != null){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl))
            requireActivity().startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Web sitesine giderken bir hata oluştu", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}