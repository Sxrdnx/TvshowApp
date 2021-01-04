package com.example.tvshowapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.WatchlistAdapter
import com.example.tvshowapp.databinding.ActivityWatchlistBinding
import com.example.tvshowapp.listeners.WatchlistListener
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.utilities.TempDataHolder
import com.example.tvshowapp.viewmodels.WatchlistVieModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WatchlistActivity : AppCompatActivity(), WatchlistListener {
    private lateinit var activityWatchlistBinding: ActivityWatchlistBinding
    private lateinit var viewModel: WatchlistVieModel
    private lateinit var watchlistAdapter: WatchlistAdapter
    private val watchlist = mutableListOf<TVShow>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist)
        doInitialization()
    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(WatchlistVieModel::class.java)
        activityWatchlistBinding.imageBack.setOnClickListener { onBackPressed() }
        loadWatchlist()
    }

    private fun loadWatchlist() {
        activityWatchlistBinding.isLoading = true
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activityWatchlistBinding.isLoading = false
                    if (watchlist.size > 0)
                        watchlist.clear()
                    watchlist.addAll(it)
                    watchlistAdapter = WatchlistAdapter(watchlist, this)
                    activityWatchlistBinding.watchlistRecyclerView.adapter = watchlistAdapter
                    activityWatchlistBinding.watchlistRecyclerView.visibility = View.VISIBLE
                    compositeDisposable.dispose()
                })
    }

    override fun onResume() {
        super.onResume()
        if (TempDataHolder.IS_WATCHTLIST_UPDATE) {
            loadWatchlist()
            TempDataHolder.IS_WATCHTLIST_UPDATE = false
        }
    }


    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java).putExtra("tvShow", tvShow)
        startActivity(intent)
    }

    override fun removeTVShowFromWatchlist(tvShow: TVShow, position: Int) {
        val compositeDisposableDorDelete = CompositeDisposable()
        compositeDisposableDorDelete.add(viewModel.removeTVShowFromWatchlist(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    watchlist.remove(tvShow)
                    watchlistAdapter.notifyItemRemoved(position)
                    watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.itemCount)
                    compositeDisposableDorDelete.dispose()
                })
    }

}