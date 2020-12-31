package com.example.tvshowapp.dao

import androidx.room.*
import com.example.tvshowapp.models.TVShow
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tvShows")
    fun getWatchlist(): Flowable<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToWatchlist(tvShow :TVShow):Completable

    @Delete
    fun removeFromWatchlist(tvShow: TVShow)
    
}