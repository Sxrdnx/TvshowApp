package com.example.tvshowapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvshowapp.dao.TVShowDao
import com.example.tvshowapp.models.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)

abstract class TVShowsDatabase: RoomDatabase() {
    abstract fun tvShowDao(): TVShowDao
}