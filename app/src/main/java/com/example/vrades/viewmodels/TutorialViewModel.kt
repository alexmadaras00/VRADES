package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TutorialViewModel : ViewModel() {

    private val _currentState = MutableLiveData<Int>()
    val currentState: LiveData<Int>
        get() = _currentState

    private val _onNextPage = SingleLiveEvent<Void>()
    val onNextPage: LiveData<Void>
        get() = _onNextPage

    private val _onNavigateToHome = SingleLiveEvent<Void>()
    val onNavigateToHome: LiveData<Void>
        get() = _onNavigateToHome

    init {
        _currentState.value = 0
    }

    fun onNavigateToHomeScreenClicked() {
        _onNavigateToHome.call()
    }

    fun onNextPageClicked() {
        _onNextPage.call()
        _currentState.value = _currentState.value!! + 1
    }

    fun getCurrentStateData(): Int {
        return _currentState.value!!
    }

    fun setCurrentStateData(data: Int) {
        _currentState.value = data
    }

    override fun onCleared() {
        _currentState.value = 0
    }


}

