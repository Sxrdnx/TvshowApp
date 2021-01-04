package com.example.tvshowapp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.tvshowapp.database.TVShowsDatabase
import com.example.tvshowapp.models.TVShow
import io.reactivex.Completable
import io.reactivex.Flowable

class WatchlistVieModel(application: Application): AndroidViewModel(application) {
    private val tvShowDatabase : TVShowsDatabase by lazy { getTVShowDatabase(application)}

     fun loadWatchlist (): Flowable<List<TVShow>>{
        return tvShowDatabase.tvShowDao().getWatchlist()
    }

    fun removeTVShowFromWatchlist(tvShow:TVShow): Completable{
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShow)
    }

    private  fun getTVShowDatabase(context: Context): TVShowsDatabase {
        return Room.databaseBuilder(
            context,
            TVShowsDatabase::class.java,
            "tv_shows_db").build()
    }
}