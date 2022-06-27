package com.example.vrades.presentation.viewmodels

import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.*
import com.example.vrades.domain.model.Test
import com.example.vrades.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.domain.use_cases.vrades_repository.VradesUseCases
import com.example.vrades.presentation.enums.AudioState
import com.example.vrades.presentation.enums.TestState
import com.example.vrades.presentation.enums.WritingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val useCasesProfile: ProfileUseCases,
    private val useCasesVrades: VradesUseCases,
) : ViewModel() {
    private var faceDetectionResult = mapOf<String, Float>()
    private var audioDetectionResult = mapOf<String, Float>()
    private var digitalWritingDetectionResult = mapOf<String, Float>()
    private var averageDetectionResult = mapOf<String, Float>()
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var textToSpeechEngine: TextToSpeech

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

    fun setAudioInStorage(picture: Uri) = liveData(Dispatchers.IO) {
        useCasesProfile.setDetectedAudioInStorage(picture).collect {
            emit(it)
        }
    }

    fun getFinalDetectionResult(): String {
        var max = 0.0f
        var result = ""
        averageDetectionResult =
            faceDetectionResult + audioDetectionResult + digitalWritingDetectionResult
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

    fun getPercentageOfResults(): Map<String, Float> {
        averageDetectionResult =
            faceDetectionResult + audioDetectionResult + digitalWritingDetectionResult
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
        faceDetectionResult = result
    }

    fun setAudioDetectedResult(result: MutableMap<String, Float>) {
        audioDetectionResult = result
    }

    fun setDigitalWritingDetectedResult(result: MutableMap<String, Float>) {
        digitalWritingDetectionResult = result
    }

    fun getStateCount(): Int? {
        return _currentStateCount.value
    }

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

    fun displaySpeechRecognizer() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale.getDefault())
        })
    }



}