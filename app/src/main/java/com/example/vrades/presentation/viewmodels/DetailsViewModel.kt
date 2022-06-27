package com.example.vrades.presentation.viewmodels

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.R
import com.example.vrades.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.presentation.enums.TestState
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private var chartData = ArrayList<PieEntry>()
    private val emotionsPercentage = HashMap<String, Float>()
    private val colors = ArrayList<Int>()
    private val state = MutableLiveData<TestState>()
    private val label = "Percentage of Emotions"
    private lateinit var pieDataSet: PieDataSet
    private lateinit var pieData: PieData

    fun getUser() = liveData(Dispatchers.IO) {

        profileUseCases.getUserById().collect {
            emit(it)
        }

    }


    fun getState(): TestState? {
        return state.value
    }

    private fun configPieData() {
        emotionsPercentage["ANGER"] = 0.6f
        emotionsPercentage["LOVE"] = 0.1f
        emotionsPercentage["SAD"] = 0.2f
        emotionsPercentage["HAPPY"] = 0.01f
        emotionsPercentage["FEAR"] = 0.01f
        emotionsPercentage["DISGUST"] = 0.03f
        emotionsPercentage["NEUTRAL"] = 0.05f
        emotionsPercentage["LOVE"] = 0.0f

        for (el in emotionsPercentage.keys)
            if (emotionsPercentage.keys.isNotEmpty()) {
                chartData.add(PieEntry(emotionsPercentage[el]!!.toFloat(), el))
            }
        colors.add(Color.parseColor("#04C3CB"))
        colors.add(R.color.black)
        colors.add(Color.parseColor("#FFBB86FC"))
        colors.add(Color.parseColor("#FF6200EE"))
        colors.add(R.color.purple_700)
        colors.add(Color.parseColor("#FF5733"))
        colors.add(Color.parseColor("#7982C1"))
        colors.add(Color.parseColor("#4E5151"))

        pieDataSet = PieDataSet(chartData, label)
        pieDataSet.valueTextSize = 18f
        pieDataSet.colors = colors
        pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        pieData.setValueTextColor(Color.parseColor("#FFFFFFFF"))
    }


    fun getData(): PieData {
        configPieData()
        return pieData
    }

    fun getMax(): String {
        return emotionsPercentage.maxByOrNull { it.value }!!.key
    }


}