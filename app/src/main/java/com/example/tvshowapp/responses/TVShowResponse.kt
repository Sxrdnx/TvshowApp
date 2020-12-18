package com.example.tvshowapp.responses

import com.example.tvshowapp.models.TVShow
import com.google.gson.annotations.SerializedName

data class TVShowResponse(
        @SerializedName("page") val page: Int =0,
        @SerializedName("pages") val totalPages: Int=0,
        @SerializedName("tv_shows") val tvShows: List<TVShow>)
