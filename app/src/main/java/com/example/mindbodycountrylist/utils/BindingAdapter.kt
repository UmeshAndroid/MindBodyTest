package com.example.mindbodycountrylist.utils

import android.text.TextUtils.split
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mindbodycountrylist.R
import java.util.*

object BindigUtils {
    @BindingAdapter("imageUrl", "code")
    @JvmStatic
    fun ImageView.setImageUrl(url: String?, code: String) {
        Glide.with(this.context)
            .load("$url${code.toLowerCase(Locale.getDefault())}.png")
            .apply(
                RequestOptions().placeholder(R.drawable.image_not_available)
                    .error(R.drawable.image_not_available)
            )
            .into(this)
    }

    @BindingAdapter("android:setCountryName")
    @JvmStatic
    fun TextView.setCountryName(countryName: String) {
        val text = countryName.split(" ").joinToString(" ") { it.toLowerCase().capitalize() }
        this.text = text
    }
}
