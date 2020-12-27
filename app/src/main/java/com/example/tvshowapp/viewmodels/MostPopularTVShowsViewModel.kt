package com.example.tvshowapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvshowapp.repositories.MostPopularTVShowsRepository
import com.example.tvshowapp.responses.TVShowResponse
//ViewModel
class MostPopularTVShowsViewModel: ViewModel(){
     private val mostPopularTVShowsRepository: MostPopularTVShowsRepository= MostPopularTVShowsRepository()

     fun getMostPopularTVShows(page : Int): LiveData<TVShowResponse>{
        return mostPopularTVShowsRepository.getMostPopularTVShows(page)
    }
}