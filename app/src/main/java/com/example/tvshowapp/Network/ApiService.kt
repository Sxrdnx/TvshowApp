package com.example.tvshowapp.Network

import com.example.tvshowapp.responses.TVShowResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path

//interfas para optener el numero de show mas populars
interface ApiService {
    @GET("most-popular")
    fun getMostPopularTVShow(@Query("page")page: Int): Call<TVShowResponse>
}