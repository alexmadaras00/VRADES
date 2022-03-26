package com.example.vrades.ui.binding


import androidx.databinding.BindingAdapter
import com.anychart.AnyChartView
import com.anychart.charts.Pie

@BindingAdapter("android:displayChart")
fun setChart(chart: AnyChartView, pie: Pie) {
    chart.setChart(pie)

}




