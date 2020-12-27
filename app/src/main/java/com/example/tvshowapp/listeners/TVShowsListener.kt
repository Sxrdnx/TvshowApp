package com.example.tvshowapp.listeners

import com.example.tvshowapp.models.TVShow

interface TVShowsListener {
    fun onTVShowClicked(tvShow: TVShow)
}