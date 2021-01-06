package com.example.tvshowapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvshowapp.repositories.SearchTVShowRepository
import com.example.tvshowapp.responses.TVShowResponse

class SearchViewModel: ViewModel() {
    private val  searchTVShowRepository: SearchTVShowRepository = SearchTVShowRepository()

    fun searchTVShow(query: String, page : Int): LiveData<TVShowResponse>{
        return searchTVShowRepository.searchTVShow(query,page)
    }
}