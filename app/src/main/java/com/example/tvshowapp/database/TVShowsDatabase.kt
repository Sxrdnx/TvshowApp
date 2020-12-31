package com.example.tvshowapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvshowapp.dao.TVShowDao
import com.example.tvshowapp.models.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)

abstract class TVShowsDatabase: RoomDatabase() {
     /*companion object{
          lateinit var tvShowsDatabase: TVShowsDatabase
          fun getTVShowDatabase(context: Context): TVShowsDatabase {
              tvShowsDatabase = Room.databaseBuilder(
                      context,
                      TVShowsDatabase::class.java,
                      "tv_shows_db"
              ).build()
              return tvShowsDatabase
          }
    }*/
    /*Se agrego las siguientes dependencias para solucionar el problema
    *kapt "androidx.lifecycle:lifecycle-compiler:2.2.0"
    *kapt "androidx.room:room-compiler:2.2.6"*/


    abstract fun tvShowDao(): TVShowDao

}