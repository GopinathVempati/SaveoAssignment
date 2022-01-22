package com.saveo.utils

import android.graphics.Rect
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : ViewModel> initViewModel(): T {
    return ViewModelProvider.NewInstanceFactory().create(T::class.java)
}

fun String?.checkNull(value: String = ""): String {
    return if (this.isNullOrEmpty()) value else this
}

fun AppCompatImageView.loadImageFromUrl(url: String?) {
    url?.let {
//        val glideUrl = GlideUrl(it)
        Glide.with(this).load(Constants.IMGE_BASE_URL.plus(it)).into(this)
    }
}

fun getItemDecoration(left: Int? = 0, top: Int? = 0, right: Int? = 0, bottom: Int? = 0): RecyclerView.ItemDecoration {
    return object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(left!!, top!!, right!!, bottom!!)
        }
    }
}

fun String?.toDispalyFormat(): String {
    if (this == null || this == "null") return "N/A"
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat: DateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
//    inputFormat.timeZone = TimeZone.getTimeZone("IST")
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}
