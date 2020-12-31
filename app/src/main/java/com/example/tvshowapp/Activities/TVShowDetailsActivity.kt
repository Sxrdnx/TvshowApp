package com.example.tvshowapp.Activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.tvshowapp.R
import com.example.tvshowapp.adapters.EpisodesAdapter
import com.example.tvshowapp.adapters.ImageSliderAdapter
import com.example.tvshowapp.databinding.ActivityTVShowDetailsBinding
import com.example.tvshowapp.databinding.LayoutEpisodesBottonSheetBinding
import com.example.tvshowapp.models.TVShow
import com.example.tvshowapp.viewmodels.TVShowDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var activityTVShowDetailsBinding: ActivityTVShowDetailsBinding
    private lateinit var tvShowDetailViewModel: TVShowDetailsViewModel
    private lateinit var episodeBottomSheetDialog: BottomSheetDialog
    private lateinit var layoutEpisodesBottonSheetBinding: LayoutEpisodesBottonSheetBinding
    private lateinit var tvShow: TVShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         activityTVShowDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_show_details)
        doInitialization()
    }

    private fun doInitialization(){
        tvShowDetailViewModel= ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)
        activityTVShowDetailsBinding.imageBack.setOnClickListener { onBackPressed() }
        tvShow = intent.getSerializableExtra("tvShow") as TVShow
        getTVShowDetails()
    }

    private fun getTVShowDetails(){
        activityTVShowDetailsBinding.isLoading=true
        val tvShowId:String = tvShow.id.toString()
        tvShowDetailViewModel.getTVShowDetails(tvShowId).observe(this, { tvShowDetailResponse ->
            activityTVShowDetailsBinding.isLoading = false
            if (tvShowDetailResponse != null) {
                loadImageSlider(tvShowDetailResponse.tvShowDetails.pictures)
                activityTVShowDetailsBinding.tvShowImageURL = tvShowDetailResponse.tvShowDetails.imagePath
                activityTVShowDetailsBinding.imageTVShow.visibility = View.VISIBLE
                activityTVShowDetailsBinding.description = HtmlCompat.fromHtml(tvShowDetailResponse.tvShowDetails.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                activityTVShowDetailsBinding.textDescription.visibility = View.VISIBLE
                activityTVShowDetailsBinding.textReadMore.visibility = View.VISIBLE
                activityTVShowDetailsBinding.textReadMore.setOnClickListener {
                    if (activityTVShowDetailsBinding.textReadMore.text.toString() == ("Read More")) {
                        activityTVShowDetailsBinding.textDescription.maxLines = Integer.MAX_VALUE
                        activityTVShowDetailsBinding.textDescription.ellipsize = null
                        activityTVShowDetailsBinding.textReadMore.text = getString(R.string.read_less)
                    } else {
                        activityTVShowDetailsBinding.textDescription.maxLines = 4
                        activityTVShowDetailsBinding.textDescription.ellipsize = TextUtils.TruncateAt.END
                        activityTVShowDetailsBinding.textReadMore.text = getString(R.string.read_more)
                    }
                }
                activityTVShowDetailsBinding.rating = String.format(
                        Locale.getDefault(),
                        "%.2f",
                        tvShowDetailResponse.tvShowDetails.rating.toDoubleOrNull()
                )
                activityTVShowDetailsBinding.genre = tvShowDetailResponse.tvShowDetails.genres[0]
                activityTVShowDetailsBinding.runtime = tvShowDetailResponse.tvShowDetails.runtime + "   Min"
                activityTVShowDetailsBinding.viewDivider1.visibility = View.VISIBLE
                activityTVShowDetailsBinding.layoutMisc.visibility = View.VISIBLE
                activityTVShowDetailsBinding.viewDivider2.visibility = View.VISIBLE
                activityTVShowDetailsBinding.buttonWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(tvShowDetailResponse.tvShowDetails.url)
                    startActivity(intent)
                }
                activityTVShowDetailsBinding.buttonWebsite.visibility = View.VISIBLE
                activityTVShowDetailsBinding.buttonEpisodes.visibility = View.VISIBLE
                activityTVShowDetailsBinding.buttonEpisodes.setOnClickListener {

                    episodeBottomSheetDialog = BottomSheetDialog(this)
                    layoutEpisodesBottonSheetBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(this),
                            R.layout.layout_episodes_botton_sheet,
                            findViewById(R.id.episodesContainer),
                            false
                    )
                    episodeBottomSheetDialog.setContentView(layoutEpisodesBottonSheetBinding.root)
                    layoutEpisodesBottonSheetBinding.episodesRecyclerView.adapter = EpisodesAdapter(tvShowDetailResponse.tvShowDetails.episodes)
                    layoutEpisodesBottonSheetBinding.textTitle.text =
                            String.format("Episodes | %s ", tvShow.name)
                    layoutEpisodesBottonSheetBinding.imageClose.setOnClickListener {
                        episodeBottomSheetDialog.dismiss()
                    }
                    episodeBottomSheetDialog.show()
                }
                activityTVShowDetailsBinding.imageWatchlist.setOnClickListener {
                    CompositeDisposable().add(tvShowDetailViewModel.addToWatchlist(tvShow)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                activityTVShowDetailsBinding.imageWatchlist.setImageResource(R.drawable.ic_added)
                                Toast.makeText(applicationContext, "Added to watchlist ", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
                activityTVShowDetailsBinding.imageWatchlist.visibility=View.VISIBLE
                loadBasicTVShowDetails()
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

    private fun loadBasicTVShowDetails(){
        activityTVShowDetailsBinding.tvShowName = tvShow.name
        activityTVShowDetailsBinding.networkCountry = tvShow.network + "(" +
                tvShow.country + ")"
        activityTVShowDetailsBinding.status = tvShow.status
        activityTVShowDetailsBinding.startedDate = tvShow.startDate
        activityTVShowDetailsBinding.textNetworkCountry.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStatus.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStarted.visibility = View.VISIBLE
    }
}