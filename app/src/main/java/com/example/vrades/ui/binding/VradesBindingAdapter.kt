package com.example.vrades.ui.binding


import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.vrades.viewmodels.TutorialViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("android:setData")
fun setData(chart: PieChart, pieData: PieData) {
    chart.data = pieData
}
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: CircleImageView, url: String?) {
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

@BindingAdapter("backgroundImageUrl")
fun loadImageUrl(view: View, name: String) {
    Glide.with(view.context).load(name).into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
        ) {
            view.background = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            TODO("Not yet implemented")
        }


    })
}
@BindingAdapter("iconImageUrl")
fun loadIconImageUrl(view: MaterialButton, name: String) {
    Glide.with(view.context).load(name).into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
        ) {
            view.icon = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            TODO("Not yet implemented")
        }


    })
}
@BindingAdapter("foregroundImageUrl")
fun loadForegroundImageUrl(view: View, name: String) {
    Glide.with(view.context).load(name).into(object : CustomTarget<Drawable>() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onResourceReady(
            resource: Drawable,
            transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
        ) {
            view.foreground = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            TODO("Not yet implemented")
        }


    })
}





