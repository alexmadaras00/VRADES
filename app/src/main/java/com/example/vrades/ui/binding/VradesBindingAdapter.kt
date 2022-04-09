package com.example.vrades.ui.binding


import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vrades.R
import com.example.vrades.viewmodels.TutorialViewModel
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

@BindingAdapter("customOnClickListener")
fun View.customOnClickListener(viewModel: TutorialViewModel) {
    setOnClickListener {
        viewModel.setCurrentStateData(0)
        viewModel.onNextPageClicked()
    }
}




