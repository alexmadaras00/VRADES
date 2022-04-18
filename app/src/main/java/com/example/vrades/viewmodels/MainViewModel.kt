package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _onNavigateToTest = SingleLiveEvent<Void>()
    val onNavigateToTest: LiveData<Void>
        get() = _onNavigateToTest

    private fun onNavigateToTestClicked(){
        _onNavigateToTest.call()
    }


}
