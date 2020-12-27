package com.example.tvshowapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.ImageSliderAdapter
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
           if (tvShowDetailResponse != null){
                loadImageSlider(tvShowDetailResponse.tvShowDetails.pictures)
            }
        })
    }

    private fun loadImageSlider(sliderImages: List<String>){
        activityTVShowDetailsBinding.sliderViewPager.offscreenPageLimit=1
        activityTVShowDetailsBinding.sliderViewPager.adapter=ImageSliderAdapter(sliderImages)
        activityTVShowDetailsBinding.sliderViewPager.visibility= View.VISIBLE
        activityTVShowDetailsBinding.viewFadingEdge.visibility=View.VISIBLE
        setupSliderIndicators(sliderImages.size)
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }

        })
    }

    private fun setupSliderIndicators(count :Int){
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i]=ImageView(applicationContext)
            indicators[i]!!.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_inidcator_inactive))
            indicators[i]!!.layoutParams=layoutParams
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i])
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.visibility=View.VISIBLE
        setCurrentSliderIndicator(0)
    }
    private fun setCurrentSliderIndicator(position: Int){
        val childCount = activityTVShowDetailsBinding.layoutSliderIndicators.childCount
        for (i in 0 until childCount){
            val imageView: ImageView = activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i) as ImageView
            if (i== position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext,R.drawable.background_slider_indicator_active))
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext,R.drawable.background_slider_inidcator_inactive))
            }
        }
    }
}