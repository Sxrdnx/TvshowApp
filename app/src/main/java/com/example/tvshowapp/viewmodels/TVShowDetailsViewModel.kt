package com.example.tvshowapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvshowapp.repositories.TVShowDetailsRepository
import com.example.tvshowapp.responses.TVShowDetailsResponse

class TVShowDetailsViewModel: ViewModel() {
    private val tvShowDetailsRepository: TVShowDetailsRepository= TVShowDetailsRepository()
    
    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }
}