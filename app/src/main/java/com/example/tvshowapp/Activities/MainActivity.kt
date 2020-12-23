package com.example.tvshowapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.TVShowsAdapter
import com.example.tvshowapp.databinding.ActivityMainBinding
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private lateinit var activityMainBinding : ActivityMainBinding
    private  val tvShows= mutableListOf<TVShow>()
    private lateinit  var tvShowAdapter:TVShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        doInitialization()
    }
    private fun doInitialization(){
        activityMainBinding.tvShowRecycleView.setHasFixedSize(true)
        viewModel= ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        tvShowAdapter= TVShowsAdapter(tvShows)
        activityMainBinding.tvShowRecycleView.adapter=tvShowAdapter
        getMostPopularTVShows()

    }

    private fun getMostPopularTVShows(){
        activityMainBinding.isLoading=true
        viewModel.getMostPopularTVShows(0).observe(this, { mostPopularTVShowsResponse ->
                activityMainBinding.isLoading=false
                if (mostPopularTVShowsResponse != null){
                    tvShows.addAll(mostPopularTVShowsResponse.tvShows)
                     tvShowAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this,"ke pdo esta empty v:< ", Toast.LENGTH_SHORT).show()
                }
            })
    }
}