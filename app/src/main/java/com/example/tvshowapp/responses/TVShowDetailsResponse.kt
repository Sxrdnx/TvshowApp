package com.example.tvshowapp.responses

import com.example.tvshowapp.models.TVShowDetails
import com.google.gson.annotations.SerializedName

data class TVShowDetailsResponse (@SerializedName("tvShow") val tvShowDetails:TVShowDetails)
