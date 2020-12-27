package com.example.tvshowapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.ActivityTVShowDetailsBinding
import com.example.tvshowapp.viewmodels.TVShowDetailsViewModel

class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var activityTVShowDetailsBinding: ActivityTVShowDetailsBinding
    private lateinit var tvShowDetailViewModel: TVShowDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         activityTVShowDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_show_details)
        doInitialization()
    }
    private fun doInitialization(){
        tvShowDetailViewModel= ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)
        getTVshowDetails()
    }
    private fun getTVshowDetails(){
        activityTVShowDetailsBinding.isLoading=true
        val tvShowId:String = intent.getIntExtra("id",-1).toString()
        tvShowDetailViewModel.getTVShowDetails(tvShowId).observe(this,{tvShowDetailResponse->
            activityTVShowDetailsBinding.isLoading=false
            Toast.makeText(applicationContext,tvShowDetailResponse.tvShowDetails.url,Toast.LENGTH_SHORT).show()
        })
    }
}