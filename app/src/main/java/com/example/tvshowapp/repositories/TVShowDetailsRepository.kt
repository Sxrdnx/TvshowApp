package com.example.tvshowapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvshowapp.Network.ApiClient
import com.example.tvshowapp.Network.ApiService
import com.example.tvshowapp.responses.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TVShowDetailsRepository {
    private val ApiServ: ApiService= ApiClient.getRetrofit().create(ApiService::class.java)

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse>{

        val data:MutableLiveData<TVShowDetailsResponse> = MutableLiveData()
        ApiServ.getTVShowDetails(tvShowId).enqueue(object : Callback<TVShowDetailsResponse>{
            override fun onResponse(call: Call<TVShowDetailsResponse>, response: Response<TVShowDetailsResponse>) {
                data.value= response.body()
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                data.value=null
            }

        })

        return data
    }
}