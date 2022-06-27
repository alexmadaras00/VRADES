package com.example.vrades.presentation.ui.binding


import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.vrades.R
import com.example.vrades.presentation.viewmodels.TutorialViewModel
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
            println("Loading...")
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
            view.icon = placeholder
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
            view.foreground = placeholder
        }


    })
}
@BindingAdapter("imageUrl")
fun ImageView.loadUrl(url: String) {

    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_baseline_assessment_24)
        .error(R.drawable.ic_baseline_cancel_24)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}





