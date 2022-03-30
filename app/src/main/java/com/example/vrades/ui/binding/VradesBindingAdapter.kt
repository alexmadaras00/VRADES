package com.example.vrades.ui.binding


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.anychart.AnyChartView
import com.anychart.charts.Pie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("android:displayChart")
fun setChart(chart: AnyChartView, pie: Pie) {
    chart.setChart(pie)

}
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}




