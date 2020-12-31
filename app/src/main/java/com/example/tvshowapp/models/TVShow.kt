package com.example.tvshowapp.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tvShows")
data class TVShow(
        @PrimaryKey
        @SerializedName("id") val id: Int=0,
        @SerializedName("name") val name: String="",
        @SerializedName("start_date") val startDate: String="",
        @SerializedName("country") val country: String="",
        @SerializedName("network") val network: String ="",
        @SerializedName("status") val status: String ="",
        @SerializedName("image_thumbnail_path") val thumbnail: String=""): Serializable
