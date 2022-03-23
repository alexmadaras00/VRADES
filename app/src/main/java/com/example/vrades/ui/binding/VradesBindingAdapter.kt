package com.example.vrades.ui.binding

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.anychart.AnyChart
import com.anychart.AnyChartFormat
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie

@BindingAdapter("tools:configPie")
fun setPie(pie: Pie, data: ArrayList<DataEntry>){
    pie.data(data)
}
@BindingAdapter("android:displayChart")
fun setChart(chart: AnyChartView, pie: Pie) {
    chart.setChart(pie)

}




