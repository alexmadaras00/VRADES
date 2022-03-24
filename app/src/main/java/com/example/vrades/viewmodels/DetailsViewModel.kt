package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie

class DetailsViewModel : ViewModel() {
    var chartData = ArrayList<DataEntry>()
    var emotions = ArrayList<String>()
    val pie = AnyChart.pie()

    private fun addData() {

        chartData.add(ValueDataEntry("JOY", 45))
        emotions.add("SADNESS")
        emotions.add("HAPPINESS")
        emotions.add("ANXIETY")
        emotions.add("FEAR")
        emotions.add("EXCITEMENT")
        emotions.add("WORRY")
        emotions.add("STRESS")
        emotions.add("CONTENT")
        for (el in emotions)
            chartData.add(ValueDataEntry(el, 45))


    }

    fun getData(): ArrayList<DataEntry> {
        addData()
        return chartData
    }

    fun configPie() {
        val data = getData()
        pie.data(data)
    }

    fun getPieChart(): Pie {
        configPie()
        return pie
    }
}