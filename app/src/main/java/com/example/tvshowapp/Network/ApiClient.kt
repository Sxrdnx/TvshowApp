package com.example.tvshowapp.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//llamada a retrofit con la url generica (objeto estatico)
class ApiClient {
    companion object{
         fun getRetrofit(): Retrofit{
             return Retrofit.Builder()
                     .baseUrl("https://www.episodate.com/api/")
                     .addConverterFactory(GsonConverterFactory.create())
                     .build()
        }
    }

}