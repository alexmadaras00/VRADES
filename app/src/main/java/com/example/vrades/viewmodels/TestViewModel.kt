package com.example.vrades.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vrades.enums.TestState

class TestViewModel : ViewModel() {
    private val state = MutableLiveData<TestState>()

    init {
        state.value = TestState.TEST_STARTED
    }

    fun setState(state: TestState) {
        this.state.value = state
    }

    fun getState(): TestState? {
        return state.value
    }


}