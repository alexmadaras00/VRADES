package com.example.vrades.ui.binding


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData

@BindingAdapter("android:setData")
fun setData(chart: PieChart, pieData: PieData) {
    chart.data = pieData
}
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}




