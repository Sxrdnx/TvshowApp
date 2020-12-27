package com.example.tvshowapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowapp.R
import com.example.tvshowapp.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private val sliderImages:List<String>): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>(){
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater= LayoutInflater.from(parent.context)
        val sliderImageBinding:ItemContainerSliderImageBinding=DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_slider_image,parent,false)
        return ViewHolder(sliderImageBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sliderImages[position])
    
    override fun getItemCount() = sliderImages.size          

    class ViewHolder(private val itemContainerSliderImageBinding: ItemContainerSliderImageBinding): RecyclerView.ViewHolder(itemContainerSliderImageBinding.root){
        fun bind (imageURL: String){
            itemContainerSliderImageBinding.imageURL=imageURL
        }
    }
}
