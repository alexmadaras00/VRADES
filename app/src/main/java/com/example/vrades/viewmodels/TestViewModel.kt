package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vrades.R
import com.example.vrades.enums.AudioState
import com.example.vrades.enums.TestState
import com.example.vrades.enums.WritingState

class TestViewModel : ViewModel() {
    private val states = TestState.values()
    private val audioStates = AudioState.values()
    private val writingStates = WritingState.values()

    private val _currentStateCount = MutableLiveData<Int>()
    private val currentStateCount: LiveData<Int>
        get() = _currentStateCount

    private val _currentAudioStateCount = MutableLiveData<Int>()
    private val currentAudioStateCount: LiveData<Int>
        get() = _currentAudioStateCount

    private val _currentWritingStateCount = MutableLiveData<Int>()
    private val currentWritingStateCount: LiveData<Int>
        get() = _currentWritingStateCount

    private val _onNextPage = SingleLiveEvent<Void>()
    val onNextPage: LiveData<Void>
        get() = _onNextPage

    private val _onNavigateToDetails = SingleLiveEvent<Void>()
    val onNavigateToDetails: LiveData<Void>
        get() = _onNavigateToDetails

    init {
        _currentStateCount.value = TestState.TEST_STARTED.position
        _currentAudioStateCount.value = AudioState.START_RECORDING.ordinal
        _currentWritingStateCount.value = WritingState.WRITING.ordinal
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
        _currentStateCount.value = _currentStateCount.value!!+1
    }

    override fun onCleared() {
        _currentStateCount.value = 0
    }


}