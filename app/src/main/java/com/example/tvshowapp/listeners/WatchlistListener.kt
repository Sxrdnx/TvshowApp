package com.example.tvshowapp.listeners

import com.example.tvshowapp.models.TVShow

interface WatchlistListener {
    fun onTVShowClicked(tvShow: TVShow)

    fun removeTVShowFromWatchlist(tvShow: TVShow, position : Int)
}