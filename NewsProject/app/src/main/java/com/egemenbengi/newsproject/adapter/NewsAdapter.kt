package com.egemenbengi.newsproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.egemenbengi.newsproject.databinding.MainRowBinding
import com.egemenbengi.newsproject.model.News
import com.egemenbengi.newsproject.view.MainFragmentDirections

class NewsAdapter(val newsList: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: MainRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = MainRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateNews(newNewsList: List<News>){
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.textNewsNameID.text = newsList[position].name
        holder.itemView.setOnClickListener { view ->
            val action = MainFragmentDirections.actionMainFragmentToNewsDetailFragment(newsList[position].uuid)
            Navigation.findNavController(view).navigate(action)
        }
    }
}