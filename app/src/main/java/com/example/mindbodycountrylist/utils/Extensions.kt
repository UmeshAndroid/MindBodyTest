package com.example.mindbodycountrylist.utils

import android.content.Context
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Int.getDate(): String {
    val format = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.setTimeZone(TimeZone.getDefault())
    return sdf.format(Date(this.toLong() * 1000))
}

fun Boolean?.getSuccessImage(context: Context): Int {
    return when (this) {
        true -> context.resources.getIdentifier("ic_success", "drawable", context.packageName)
        false -> context.resources.getIdentifier("ic_fail", "drawable", context.packageName)
        else -> context.resources.getIdentifier("ic_fail", "drawable", context.packageName)
    }


}

fun Int?.getFormatteDate(): String {
    return when (this) {
        0, null -> "Launch Date : Not Available"
        else -> "Launch Date : ${this.getDate()}"
    }


}

fun String?.getUrl(): String {
    return when {
        this.isNullOrBlank() -> ""
        else -> this

    }
}