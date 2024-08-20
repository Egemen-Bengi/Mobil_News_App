package com.egemenbengi.newsproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egemenbengi.newsproject.R
import com.egemenbengi.newsproject.adapter.NewsAdapter
import com.egemenbengi.newsproject.databinding.FragmentMainBinding
import com.egemenbengi.newsproject.databinding.FragmentNewsDetailBinding
import com.egemenbengi.newsproject.viewmodel.NewsListViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsListViewModel
    private val adapter = NewsAdapter(arrayListOf())
    private lateinit var tag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        viewModel.refreshData("general")

        binding.recyclerMainID.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMainID.adapter = adapter

        binding.swipeRefreshId.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerMainID.visibility = View.GONE
            viewModel.refreshDataFromInternet("general")
            binding.swipeRefreshId.isRefreshing = false
        }
        binding.floatingActionButton.setOnClickListener { search(it) }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.listNews.observe(viewLifecycleOwner){ newsList ->
            adapter.updateNews(newsList)
            binding.recyclerMainID.visibility = View.VISIBLE
        }

        viewModel.progressBar.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerMainID.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun search(view: View){
        tag = binding.textEditSearchID.text.toString()
        viewModel.refreshDataFromInternet(tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}