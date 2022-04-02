package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.vrades.enums.TestState

class DetailsViewModel : ViewModel() {

    var chartData = ArrayList<DataEntry>()
    var emotions = ArrayList<String>()
    private val pie: Pie = AnyChart.pie()
    private val state = MutableLiveData<TestState>()


    fun getState(): TestState? {
        return state.value
    }

    private fun addData() {
        emotions.add("ANGER")
        emotions.add("CHILL")
        emotions.add("SADNESS")
        emotions.add("HAPPINESS")
        emotions.add("ANXIETY")
        emotions.add("FEAR")
        emotions.add("EXCITEMENT")
        emotions.add("WORRY")
        emotions.add("STRESS")
        emotions.add("CONTENT")
        for (el in emotions)
            chartData.add(ValueDataEntry(el, 10))
    }

    private fun getData(): ArrayList<DataEntry> {
        addData()
        return chartData
    }

    private fun configPie() {
        val data = getData()
        pie.data(data)
    }

    fun getPieChart(): Pie {
        configPie()
        return pie
    }
}