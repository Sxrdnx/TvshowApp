package com.example.tvshowapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.TVShowsAdapter
import com.example.tvshowapp.databinding.ActivitySearchBinding
import com.example.tvshowapp.listeners.TVShowsListener
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.viewmodels.SearchViewModel
import java.util.*

class SearchActivity : AppCompatActivity(), TVShowsListener {
    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var tvShowAdapter: TVShowsAdapter
    private val tvShows = mutableListOf<TVShow>()
    private var currentPage: Int = 1
    private var totalAvailablePages: Int = 1
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        doInitialization()
    }


    private fun doInitialization() {
        activitySearchBinding.imageBack.setOnClickListener { onBackPressed() }
        activitySearchBinding.tvShowRecycleView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        tvShowAdapter = TVShowsAdapter(tvShows, this)
        activitySearchBinding.tvShowRecycleView.adapter = tvShowAdapter

        activitySearchBinding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable.toString().trim().isNotEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalAvailablePages = 1;
                                searchTVShow(editable.toString())
                            }
                        }
                    }, 800)
                } else {
                    tvShows.clear()
                    tvShowAdapter.notifyDataSetChanged()
                }
            }
        })
        activitySearchBinding.tvShowRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activitySearchBinding.tvShowRecycleView.canScrollVertically(1)) {
                    if (activitySearchBinding.inputSearch.text.toString().isNotEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1
                            searchTVShow(activitySearchBinding.inputSearch.text.toString())
                        }
                    }
                }
            }
        })
        activitySearchBinding.inputSearch.requestFocus()
    }


    private fun searchTVShow(query: String) {
        toggleLoading()
        viewModel.searchTVShow(query, currentPage).observe(this, { searchTVShowsResponse ->
            toggleLoading()
            if (searchTVShowsResponse != null) {
                totalAvailablePages = searchTVShowsResponse.totalPages
                val oldCount = tvShows.size
                tvShows.addAll(searchTVShowsResponse.tvShows)
                tvShowAdapter.notifyItemRangeChanged(oldCount, tvShows.size)
            }
        })
    }

    private fun toggleLoading() {
        if (currentPage == 1) {
            activitySearchBinding.isLoading = activitySearchBinding.isLoading != true
        } else {
            activitySearchBinding.isLoadingMore = activitySearchBinding.isLoadingMore != true
        }
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }
}