package com.example.tvshowapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.ItemContainerTvShowBinding
import com.example.tvshowapp.listeners.TVShowsListener
import com.example.tvshowapp.models.TVShow

 class TVShowsAdapter(private val items: List<TVShow>,private val tvShowListener: TVShowsListener): RecyclerView.Adapter<TVShowsAdapter.ViewHolder>(){

    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
           layoutInflater= LayoutInflater.from(parent.context)
        val binding: ItemContainerTvShowBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.item_container_tv_show,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)= holder.bind(items[position])

    override fun getItemCount()= items.size

    inner class  ViewHolder(private val itemContainerTvShowBinding: ItemContainerTvShowBinding): RecyclerView.ViewHolder(itemContainerTvShowBinding.root){
        fun bind (tvShow: TVShow){
            itemContainerTvShowBinding.tvShow=tvShow
            itemContainerTvShowBinding.executePendingBindings()
            itemContainerTvShowBinding.root.setOnClickListener {
                tvShowListener.onTVShowClicked(tvShow)
            }
        }
    }
}