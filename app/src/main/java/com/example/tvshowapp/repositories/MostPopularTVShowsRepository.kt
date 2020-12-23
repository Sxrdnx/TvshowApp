package com.example.tvshowapp.repositories

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvshowapp.Activities.MainActivity
import com.example.tvshowapp.Network.ApiClient
import com.example.tvshowapp.Network.ApiService
import com.example.tvshowapp.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularTVShowsRepository{
    private val apiServ:ApiService=ApiClient.getRetrofit().create(ApiService::class.java)

    fun getMostPopularTVShows(page :Int) :LiveData<TVShowResponse>{
         val data: MutableLiveData<TVShowResponse> = MutableLiveData()
        apiServ.getMostPopularTVShow(page).enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                data.value = response.body()
            }
            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                data.value=null
            }
        })
        return  data
    }
}