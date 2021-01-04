package com.example.tvshowapp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.tvshowapp.database.TVShowsDatabase
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.repositories.TVShowDetailsRepository
import com.example.tvshowapp.responses.TVShowDetailsResponse
import io.reactivex.Completable
import io.reactivex.Flowable

/*Nota: modificar la funcion de getTVShowDataBase a una kotlin androidExtencion o buscar la forma
* para que este simplemente en la misma clase Abstracta
* buscar en el episodio 9 apartir del minuto 4 */
class TVShowDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val tvShowDetailsRepository: TVShowDetailsRepository= TVShowDetailsRepository()
    private val tvShowsDatabase: TVShowsDatabase by lazy { getTVShowDatabase(application) }

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }

    fun addToWatchlist(tvShow: TVShow): Completable{
        return tvShowsDatabase.tvShowDao().addToWatchlist(tvShow)
    }

    fun getTVShowFromWatchlist(tvShowId :String): Flowable<TVShow>{
        return tvShowsDatabase.tvShowDao().getTVShowFromWatchlist(tvShowId)
    }

    fun removeTVShowFromWatchlist(tvShow: TVShow): Completable{
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShow)
    }

   private  fun getTVShowDatabase(context: Context): TVShowsDatabase {
       return Room.databaseBuilder(
                context,
                TVShowsDatabase::class.java,
                "tv_shows_db").build()
    }
}