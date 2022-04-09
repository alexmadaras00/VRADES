package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vrades.enums.TutorialState

class TutorialViewModel : ViewModel() {
    private val states = TutorialState.values()


    private val _currentStateCount = MutableLiveData<Int>()
    private val currentStateCount: LiveData<Int>
        get() = _currentStateCount

    private val _onNextPage = SingleLiveEvent<Void>()
    val onNextPage: LiveData<Void>
        get() = _onNextPage

    private val _onNavigateToHome = SingleLiveEvent<Void>()
    val onNavigateToHome: LiveData<Void>
        get() = _onNavigateToHome

    init {
        _currentStateCount.value = 0
    }

    fun onNavigateToHomeScreenClicked() {
        _onNavigateToHome.call()
    }

    fun onNextPageClicked() {
        _onNextPage.call()
        _currentStateCount.value = _currentStateCount.value!! + 1
    }

    fun getCurrentStateData(): Int {
        return _currentStateCount.value!!
    }

    fun setCurrentStateData(data: Int) {
        _currentStateCount.value = data
    }

    fun getCurrentState(): TutorialState {
        return states[_currentStateCount.value!!]
    }

    override fun onCleared() {
        _currentStateCount.value = 0
    }


}

