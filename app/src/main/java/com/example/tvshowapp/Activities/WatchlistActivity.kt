package com.example.tvshowapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.ActivityWatchlistBinding
import com.example.tvshowapp.viewmodels.WatchlistVieModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WatchlistActivity : AppCompatActivity() {
    private lateinit var activityWatchlistBinding: ActivityWatchlistBinding
    private lateinit var viewModel : WatchlistVieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist)
        doInitialization()
    }

    private fun doInitialization(){
        viewModel = ViewModelProvider(this).get(WatchlistVieModel::class.java)
        activityWatchlistBinding.imageBack.setOnClickListener{onBackPressed()}
    }

    private fun loadWatchlist(){
        activityWatchlistBinding.isLoading = true
        val compositeDisposable=CompositeDisposable()
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                activityWatchlistBinding.isLoading = false
                Toast.makeText(applicationContext, "Wathclist: " + it.size, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onResume() {
        super.onResume()
        loadWatchlist()
    }

}