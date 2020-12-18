package com.example.tvshowapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.R
import com.example.tvshowapp.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MostPopularTVShowsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        getMostPopularTVShows()
    }
    //prueba de coneccion
    private fun getMostPopularTVShows(){
        viewModel.getMostPopularTVShows(0).observe(this, { mostPopularTVShowsResponse ->
            Toast.makeText(applicationContext, "Total Pages" + mostPopularTVShowsResponse.totalPages,
                    Toast.LENGTH_SHORT).show()})
    }
}