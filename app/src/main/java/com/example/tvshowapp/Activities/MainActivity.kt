package com.example.tvshowapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.TVShowsAdapter
import com.example.tvshowapp.databinding.ActivityMainBinding
import com.example.tvshowapp.listeners.TVShowsListener
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity(), TVShowsListener {
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private lateinit var activityMainBinding : ActivityMainBinding
    private  val tvShows= mutableListOf<TVShow>()
    private var currentPage:Int=1
    private var totalAvailablePages: Int=1
    private lateinit  var tvShowAdapter:TVShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding  = DataBindingUtil.setContentView(this,R.layout.activity_main)
        doInitialization()
    }
    private fun doInitialization(){
        activityMainBinding.tvShowRecycleView.setHasFixedSize(true)
        viewModel= ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        tvShowAdapter= TVShowsAdapter(tvShows,this)
        activityMainBinding.tvShowRecycleView.adapter=tvShowAdapter
        activityMainBinding.tvShowRecycleView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activityMainBinding.tvShowRecycleView.canScrollVertically(1)){
                    if (currentPage <=totalAvailablePages){
                        currentPage+=1
                        getMostPopularTVShows()
                    }
                }
            }
        })

        activityMainBinding.imageWatchlist.setOnClickListener{
            startActivity(Intent(applicationContext,WatchlistActivity::class.java))
        }

        activityMainBinding.imageSearch.setOnClickListener{
            startActivity(Intent(applicationContext, SearchActivity::class.java))
        }

        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows(){
        toggleLoading()
        viewModel.getMostPopularTVShows(currentPage).observe(this, { mostPopularTVShowsResponse ->
            toggleLoading()
                activityMainBinding.isLoading=false
                if (mostPopularTVShowsResponse != null){
                    totalAvailablePages=mostPopularTVShowsResponse.totalPages
                    val oldCount=tvShows.size
                    tvShows.addAll(mostPopularTVShowsResponse.tvShows)
                    tvShowAdapter.notifyItemRangeInserted(oldCount,tvShows.size)
                }else{
                    Toast.makeText(this,"ke pdo esta empty v:< ", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun toggleLoading(){
        if (currentPage ==1){
            activityMainBinding.isLoading = activityMainBinding.isLoading != true
        }else{
            activityMainBinding.isLoadingMore=activityMainBinding.isLoadingMore !=true
        }
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent =Intent(applicationContext,TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }
}