package com.example.tvshowapp.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
/*Los binding Adapters deven ser staticos por lo cual para kotlin deben creacer fuera de
* cualquie clase */
@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, URL: String?) {
    imageView.alpha = 0f
    Picasso.get().load(URL).noFade().into(imageView, object : Callback {
        override fun onSuccess() {
            imageView.animate().setDuration(300).alpha(1f).start()
        }
        override fun onError(e: Exception?) {
        }
    })
}