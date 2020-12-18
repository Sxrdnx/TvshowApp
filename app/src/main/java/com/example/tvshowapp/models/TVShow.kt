package com.example.tvshowapp.models
import com.google.gson.annotations.SerializedName
//clase de tvshow general
data class TVShow(
        @SerializedName("id") val id: Int=0,
        @SerializedName("name") val name: String="",
        @SerializedName("start_date") val startDate: String="",
        @SerializedName("country") val country: String="",
        @SerializedName("network") val network: String ="",
        @SerializedName("status") val status: String ="",
        @SerializedName("image_thumbnail_path") val thumbnail: String="")
