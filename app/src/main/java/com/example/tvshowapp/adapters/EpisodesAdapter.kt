package com.example.tvshowapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.ItemContainerEpisodeBinding
import com.example.tvshowapp.models.Episode

class EpisodesAdapter(private val episodes: List<Episode>): RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater= LayoutInflater.from(parent.context)
        val itemContainerEpisodeBinding: ItemContainerEpisodeBinding= DataBindingUtil.inflate(
            layoutInflater, R.layout.item_container_episode, parent, false)
        return ViewHolder(itemContainerEpisodeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(episodes[position])

    override fun getItemCount()=episodes.size

    class ViewHolder(private val itemContainerEpisodeBinding: ItemContainerEpisodeBinding): RecyclerView.ViewHolder(itemContainerEpisodeBinding.root){
        fun bind(episode: Episode){
            var title = "S"
            var season = episode.season
            if (season.length == 1)
                season = "0$season"

            var episodeNumber = episode.episode

            if(episodeNumber.length == 1)
                episodeNumber = "0$episodeNumber"

            episodeNumber = "E$episodeNumber"
            title = title + season + episodeNumber

            itemContainerEpisodeBinding.title = title
            itemContainerEpisodeBinding.name = episode.name
            itemContainerEpisodeBinding.airDate = episode.airDate
        }
    }
}