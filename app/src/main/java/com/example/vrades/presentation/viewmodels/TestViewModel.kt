package com.example.vrades.presentation.viewmodels

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.*
import com.example.vrades.R
import com.example.vrades.domain.model.Test
import com.example.vrades.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.domain.use_cases.vrades_repository.VradesUseCases
import com.example.vrades.presentation.enums.AudioState
import com.example.vrades.presentation.enums.TestState
import com.example.vrades.presentation.enums.WritingState
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val useCasesProfile: ProfileUseCases,
    private val useCasesVrades: VradesUseCases,
) : ViewModel() {
    private var faceDetectionResult = mutableMapOf<String, Float>()
    private var audioDetectionResult = mutableMapOf<String, Float>()
    private var digitalWritingDetectionResult = mutableMapOf<String, Float>()
    private var averageDetectionResult = mutableMapOf<String, Float>()
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private var chartData = ArrayList<PieEntry>()
    private val colors = ArrayList<Int>()
    private val state = MutableLiveData<TestState>()
    private val label = "Percentage of Emotions"
    private lateinit var pieDataSet: PieDataSet
    private lateinit var pieData: PieData

    private val states = TestState.values()
    private val audioStates = AudioState.values()
    private val writingStates = WritingState.values()

    private val _currentStateCount = MutableLiveData<Int>()
    val currentStateCount: LiveData<Int>
        get() = _currentStateCount

    private val _currentAudioStateCount = MutableLiveData<Int>()
    val currentAudioStateCount: LiveData<Int>
        get() = _currentAudioStateCount

    private val _currentWritingStateCount = MutableLiveData<Int>()
    val currentWritingStateCount: LiveData<Int>
        get() = _currentWritingStateCount

    private val _onNextPage = SingleLiveEvent<Void>()
    val onNextPage: LiveData<Void>
        get() = _onNextPage

    private val _onNavigateToDetails = SingleLiveEvent<Void>()
    val onNavigateToDetails: LiveData<Void>
        get() = _onNavigateToDetails

    fun initial(
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        startForResult = launcher
    }

    init {
        _currentStateCount.value = TestState.TEST_STARTED.position
        _currentAudioStateCount.value = AudioState.IDLE.ordinal
        _currentWritingStateCount.value = WritingState.WRITING.ordinal
        faceDetectionResult.clear()
        audioDetectionResult.clear()
        digitalWritingDetectionResult.clear()
        averageDetectionResult.clear()
    }

    //    fun getDigitalWritingDetectionResult(text: String) = liveData(Dispatchers.IO){
//        useCaseTextDetectionAPI.invoke(text).collect{
//            emit(it)
//        }
//    }
    fun getDataAudioTest() = liveData(Dispatchers.IO) {
        useCasesVrades.getDataAudioTest().collect {
            emit(it)
        }
    }

    fun getDataWritingTest() = liveData(Dispatchers.IO) {
        useCasesVrades.getDataWritingTest().collect {
            emit(it)
        }
    }
    fun getTestByDate(date: String) = liveData(Dispatchers.IO) {
        useCasesProfile.getTestByDate(date).collect{
            emit(it)
        }
    }

    fun generateAdvicesByTestResult() = liveData(Dispatchers.IO) {
        useCasesProfile.generateAdvicesByTestResult().collect {
            emit(it)
        }
    }

    fun addTestToRealtime(test: Test) = liveData(Dispatchers.IO) {
        useCasesProfile.addTestInRealtime(test).collect {
            emit(it)
        }
    }

    fun setPictureInStorage(picture: Uri) = liveData(Dispatchers.IO) {
        useCasesProfile.setDetectedMediaInStorage(picture).collect {
            emit(it)
        }
    }

    fun getFinalDetectionResult(): String {
        var max = 0.0f
        var result = ""
        println(averageDetectionResult)
        averageDetectionResult.forEach {
            if (it.value > max)
                max = it.value
        }
        averageDetectionResult.forEach {
            if (it.value == max) {
                result += if (result.isNotEmpty()) {
                    ", $it.key"
                } else {
                    it.key
                }
            }
        }
        return result
    }

    fun getPercentageOfResults(): MutableMap<String, Float> {
        println("DEBUG:")
        averageDetectionResult.clear()
        for (el in audioDetectionResult.entries) {
            println("${faceDetectionResult[el.key]} ")
            val average:Float = if (el.key != "love") {
                (faceDetectionResult[el.key]!! + audioDetectionResult[el.key]!! + digitalWritingDetectionResult[el.key]!!) / 3
            } else {
                0f
            }
            averageDetectionResult[el.key] = average
        }
        return averageDetectionResult
    }


    fun setStateCount(state: Int) {
        _currentStateCount.value = state
    }

    fun setAudioStateCount(state: Int) {
        _currentStateCount.value = state
    }

    fun setWritingStateCount(state: Int) {
        _currentWritingStateCount.value = state
    }

    fun setFaceDetectedResult(result: MutableMap<String, Float>) {
        faceDetectionResult.clear()
        faceDetectionResult = result
    }

    fun setAudioDetectedResult(result: MutableMap<String, Float>) {
        audioDetectionResult.clear()
        audioDetectionResult = result
    }

    fun setDigitalWritingDetectedResult(result: MutableMap<String, Float>) {
        digitalWritingDetectionResult.clear()
        digitalWritingDetectionResult = result
    }

    fun getStateCount(): Int? {
        return _currentStateCount.value
    }

    fun getFaceDetectionResults(): MutableMap<String, Float> = faceDetectionResult
    fun getAudioDetectionResults(): MutableMap<String, Float> = audioDetectionResult
    fun getDigitalWritingDetectionResults(): MutableMap<String, Float> =
        digitalWritingDetectionResult

    fun getCurrentState(): TestState {
        return states[_currentStateCount.value!!]
    }

    fun getCurrentAudioState(): AudioState {
        return audioStates[_currentAudioStateCount.value!!]
    }

    fun getCurrentWritingState(): WritingState {
        return writingStates[_currentWritingStateCount.value!!]
    }

    fun onNavigateToDetailsScreenClicked() {
        println("Here")
        _onNavigateToDetails.call()
    }

    fun onNextPageClicked() {
        _onNextPage.call()
        _currentStateCount.value = _currentStateCount.value!! + 1
    }

    override fun onCleared() {
        _currentStateCount.value = 0
    }

    fun getState(): TestState? {
        return state.value
    }

    private fun configPieData(array: MutableMap<String, Float>) {
        println("VIEW MODEL MAP: $array")
        chartData.clear()
        for (el in array.keys)
            if (array.keys.isNotEmpty()) {
                chartData.add(PieEntry(array[el]!!.toFloat(), el))
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


    fun getData(array: MutableMap<String, Float>): PieData {
        configPieData(array)
        return pieData
    }

    fun getMax(): String {
        return averageDetectionResult.maxByOrNull { it.value }!!.key
    }

    fun getUser() = liveData(Dispatchers.IO) {

        useCasesProfile.getUserById().collect {
            emit(it)
        }

    }


}